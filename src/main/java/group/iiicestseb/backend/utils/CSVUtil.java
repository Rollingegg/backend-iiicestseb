package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.Test;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author jh
 * @date 2020/3/3
 */
@Component
public class CSVUtil {

    @Resource
    private AffiliationMapper affMapper;
    private static AffiliationMapper affiliationMapper;
    @Resource
    private AuthorMapper autMapper;
    private static AuthorMapper authorMapper;
    @Resource
    private PaperMapper papMapper;
    private static PaperMapper paperMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);

    /**
     * 不知道有没有更好的方法解决静态注入问题
     */
    @PostConstruct
    public void init() {
        CSVUtil.affiliationMapper = this.affMapper;
        CSVUtil.authorMapper = this.autMapper;
        CSVUtil.paperMapper = this.papMapper;
    }

//    private static final Map<String, Integer> STANDARDS = new HashMap<String, Integer>() {
//        {
//            put("IEEE Terms", 1);
//            put("INSPEC Controlled Terms", 2);
//            put("INSPEC Non-Controlled Terms", 3);
//            put("Mesh Terms", 4);
//        }
//    };

    /**
     * 解析传入的csv文件，该文件应在目录下能找到
     *
     * @param filename csv文件名
     */
    public static void analyzeCsv(String filename) {
        List<String[]> lines = new LinkedList<>();
        analyzeCsvLines(filename, lines);

        // TODO: 这些是已经存在的固定数据，如作者、机构等，后面有空的话可以用redis来优化
        Map<String, Affiliation> existedAffiliation = new HashMap<>();
        Map<String, Author> existedAuthor = new HashMap<>();
        Map<String, Conference> existedConference = new HashMap<>();
        Map<String, Term> existedTerm = new HashMap<>();
        Map<String, Publisher> existedPublisher = new HashMap<>();
        // 这里的key为paperTitle+年份
        Map<String, Paper> existedPaper = new HashMap<>();

        // 初始化待解析的数据
        List<List<Affiliation>> affiliationList = new LinkedList<>();
        List<List<Author>> authorsList = new LinkedList<>();
        List<Conference> conferenceList = new LinkedList<>();
        List<Paper> paperList = new LinkedList<>();
        List<Publisher> publisherList = new LinkedList<>();
        List<List<Term>> termsList = new LinkedList<>();
        List<Publish> publishList = new LinkedList<>();
        List<PaperTerm> paperTermList = new LinkedList<>();

        analyzeAffiliation(lines, affiliationList, existedAffiliation);
        analyzeAuthor(lines, authorsList, affiliationList, existedAuthor);
        analyzeConference(filename, lines, conferenceList, existedConference);
        analyzePublisher(lines, publisherList, existedPublisher);
        analyzeTerm(lines, termsList, existedTerm);
        analyzePaper(lines, paperList, publisherList, conferenceList, existedPaper);

        analyzePublish(paperList, authorsList, publishList);
        analyzePaperTerm(paperList, termsList, paperTermList);
        // TODO: 将上面的数据全部写入数据库
        System.out.println();

    }

    private static void analyzeCsvLines(String filename, List<String[]> lines) {
        File file = new File(filename);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found" + file.getName());
            return;
        }
        String line;
        try {
            line = reader.readLine();
            String[] temp = line.split("\",\"");
            if (temp.length != 29) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            LOGGER.error("文件头读取发生未知错误，停止解析");
            return;
        } catch (RuntimeException e) {
            LOGGER.error("文件头列数错误，停止解析");
            return;
        }
        for (int i = 0; true; i++) {
            try {
                line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] parts = line.substring(1, line.length() - 1).split("\",\"");
                assert parts.length == 29;
                lines.add(parts);
            } catch (IOException e) {
                LOGGER.warn("第" + i + "行解析出错，跳过该行");
            } catch (Exception e) {
                LOGGER.warn("第" + i + "行解析发生未知错误，跳过该行");
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.warn("解析csv结束，但是关闭文件流时出了未知错误");
        }
    }

    private static void analyzeAffiliation(List<String[]> lines, List<List<Affiliation>> affiliationList, Map<String, Affiliation> existedAffiliation) {
        List<Affiliation> newAffiliations = new LinkedList<>();
        for (String[] parts : lines) {
            String[] raw = parts[2].split("; ");
            List<Affiliation> affiliations = new LinkedList<>();
            for (String name : raw) {
                name = StringUtil.stripAccents(name);
                String key = name.trim().toLowerCase();
                if (existedAffiliation.containsKey(key)) {
                    affiliations.add(existedAffiliation.get(key));
                } else {
                    Affiliation a= affiliationMapper.selectByName(name);
                    if (a == null) {
                        a = new Affiliation(name);
                        newAffiliations.add(a);
                    }
                    existedAffiliation.put(key, a);
                    affiliations.add(a);
                }
            }
            affiliationList.add(affiliations);
        }
        if (!newAffiliations.isEmpty()) {
            affiliationMapper.insertAffiliationList(newAffiliations);
        }
    }

    private static void analyzeAuthor(List<String[]> lines, List<List<Author>> authorsList, List<List<Affiliation>> affiliationList, Map<String, Author> existedAuthor) {
        List<Author> newAuthors = new LinkedList<>();
        Iterator<List<Affiliation>> outerItr = affiliationList.iterator();
        for (String[] parts : lines) {
            String[] names = parts[1].split("; ");
            List<Author> authors = new LinkedList<>();
            List<Affiliation> affiliations = outerItr.next();
            Iterator<Affiliation> itr = affiliations.iterator();
            for (String name : names) {
                name = StringUtil.stripAccents(name);
                String key = name.trim().toLowerCase();
                if (existedAuthor.containsKey(key)) {
                    authors.add(existedAuthor.get(key));
                } else {
                    Author a = authorMapper.selectByName(name);
                    if (a == null) {
                        a = new Author(name, itr.next().getId());
                        newAuthors.add(a);
                    }
                    existedAuthor.put(key, a);
                    authors.add(a);
                }
            }
            authorsList.add(authors);
        }
        if (!newAuthors.isEmpty()) {
            authorMapper.insertAuthorList(newAuthors);
        }
    }

    private static void analyzeConference(String filename, List<String[]> lines, List<Conference> conferenceList, Map<String, Conference> existedConference) {
        List<Conference> newConference = new LinkedList<>();
        Conference conference;
        String name = StringUtil.stripAccents(filename.split("\\.")[0]);
        String key = name.trim().toLowerCase();
        if (existedConference.containsKey(key)) {
            conference = existedConference.get(key);
        } else {
            conference = paperMapper.selectConferenceByName(name);
            if (conference == null) {
                conference = new Conference(name);
                newConference.add(conference);
            } else {
                conferenceList.add(conference);
                existedConference.put(key, conference);
            }
        }
        for (String[] ignored : lines) {
            conferenceList.add(conference);
        }
        if (!newConference.isEmpty()) {
            paperMapper.insertConferenceList(newConference);
        }
    }

    private static void analyzePublisher(List<String[]> lines, List<Publisher> publisherList, Map<String, Publisher> existedPublisher) {
        List<Publisher> newPublisher = new LinkedList<>();
        for (String[] parts : lines) {
            String name = StringUtil.stripAccents(parts[27]);
            String key = name.toLowerCase();
            if (existedPublisher.containsKey(key)) {
                publisherList.add(existedPublisher.get(key));
            } else {
                Publisher p = paperMapper.selectPublisherByName(name);
                if (p == null) {
                    p = new Publisher(name);
                    newPublisher.add(p);
                }
                existedPublisher.put(key, p);
                publisherList.add(p);
            }
        }
        if (!newPublisher.isEmpty()) {
            paperMapper.insertPublisherList(newPublisher);
        }
    }

    private static void analyzeTerm(List<String[]> lines, List<List<Term>> termList, Map<String, Term> existedTerm) {
        List<Term> newTerms = new LinkedList<>();
        for (String[] parts : lines) {
            List<Term> terms = new LinkedList<>();
            for (int i : new int[]{16, 17, 18, 19, 20}) {
                String[] words;
                if (parts[i].contains(";")) {
                    words = parts[i].split(";");
                } else {
                    words = parts[i].split(",");
                }
                if (words[0].isEmpty()) {
                    continue;
                }
                for (String word : words) {
                    word = StringUtil.stripAccents(word.trim().toLowerCase());
                    if (existedTerm.containsKey(word)) {
                        terms.add(existedTerm.get(word));
                    } else {
                        Term t = paperMapper.selectTermByName(word);
                        if (t == null) {
                            t = new Term(word);
                            newTerms.add(t);
                        }
                        existedTerm.put(word, t);
                        terms.add(t);
                    }
                }
            }
            termList.add(terms);
        }
        if (!newTerms.isEmpty()) {
            paperMapper.insertTermList(newTerms);
        }
    }

    private static void analyzePaper(List<String[]> lines, List<Paper> paperList, List<Publisher> publisherList, List<Conference> conferenceList, Map<String, Paper> existedPaper) {
        List<Paper> newPaper = new LinkedList<>();
        Iterator<Publisher> publisherItr = publisherList.iterator();
        Iterator<Conference> conferenceItr = conferenceList.iterator();
        for (String[] parts : lines) {
            String paperTitle = StringUtil.stripAccents(parts[0]);
            String key = paperTitle.trim().toLowerCase();
            String year = parts[5].trim();
            // paper的year加在了key的末尾
            if (existedPaper.containsKey(key + year)) {
                Paper ep = existedPaper.get(key + year);
                if (NumberUtil.string2Int(year) == ep.getPublicationYear().getYear()) {
                    paperList.add(existedPaper.get(key + year));
                    continue;
                }
            }
            LocalDateTime publishYear = DateUtil.parseYear(year);
            Paper p = paperMapper.selectByNameAndYear(paperTitle, publishYear);
            if (p == null) {
                p = new Paper();
                p.setPaperTitle(paperTitle);
                p.setPublicationTitle(parts[3]);
                p.setPublicationYear(publishYear);
                p.setStartPage(parts[8]);
                p.setEndPage(parts[9]);
                p.setPaperAbstract(parts[10]);
                p.setDoi(parts[13]);
                p.setPdfLink(parts[15]);
                p.setCitationCount(NumberUtil.string2Int(parts[21]));
                p.setReferenceCount(NumberUtil.string2Int(parts[22]));
                p.setPublisherId(publisherItr.next().getId());
                p.setConferenceId(conferenceItr.next().getId());
                p.setDocumentIdentifier(parts[28]);
                newPaper.add(p);
            }
            existedPaper.put(key + year, p);
            paperList.add(p);
        }
        if (!newPaper.isEmpty()) {
            paperMapper.insertPaperList(newPaper);
        }
    }

    private static void analyzePaperTerm(List<Paper> paperList, List<List<Term>> termsList, List<PaperTerm> paperTermList) {
        Iterator<Paper> paperItr = paperList.iterator();
        Iterator<List<Term>> termsItr = termsList.iterator();
        while (paperItr.hasNext()) {
            Paper p = paperItr.next();
            for (Term term : termsItr.next()) {
                paperTermList.add(new PaperTerm(p, term));
            }
        }
        if (!paperList.isEmpty()) {
            paperMapper.insertPaperTermList(paperTermList);
        }
    }

    private static void analyzePublish(List<Paper> paperList, List<List<Author>> authorsList, List<Publish> publishList) {
        Iterator<Paper> paperItr = paperList.iterator();
        Iterator<List<Author>> authorsItr = authorsList.iterator();
        while (paperItr.hasNext()) {
            Paper p = paperItr.next();
            for (Author author : authorsItr.next()) {
                publishList.add(new Publish(p, author));
            }
        }
        if (!publishList.isEmpty()) {
            paperMapper.insertPublishList(publishList);
        }
    }

}
