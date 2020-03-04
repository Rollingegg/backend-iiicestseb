package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.Test;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
    public static final String CSV_PATH = CSVUtil.class.getResource("/").getPath() + "csv/";

    public static final int COL_NUM = 29;
    public static final String UNKNOWN_ERROR = "未知错误";
    public static final String FILE_OPEN_ERROR = "文件流打开失败";
    public static final String FILE_CLOSE_ERROR = "解析csv结束，但是关闭文件流时出了未知错误";
    public static final String UNKNOWN_HEADER_ERROR = "文件头读取发生未知错误，停止解析";
    public static final String HEADER_FORMAT_ERROR = "文件头列数错误，请检查文件头格式";
    public static final String COL_FORMAT_ERROR = "文件内容列数错误，请检查格式，错误行号：";
    public static final String COL_READ_ERROR = "文件内容读取错误，请检查格式，错误行号：";
    public static final String COL_SEPARATOR = "\",\"";
    public static final String FILE_NOT_FOUND_ERROR = "文件不存在";

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
     * 解析已经存在的csv文件，该文件应在目录下能找到
     *
     * @param filename csv文件名
     */
    public static void analyzeExistedCSV(String filename) {
        File file = new File(CSV_PATH + filename);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            LOGGER.error(FILE_NOT_FOUND_ERROR + ": " + file.getName());
            throw new CSVException(FILE_NOT_FOUND_ERROR + ": " + file.getName());
        }
        List<String[]> lines = new LinkedList<>();
        splitCSVLines(reader, lines);
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.warn(FILE_CLOSE_ERROR);
            throw new CSVException(FILE_CLOSE_ERROR);
        }
        analyzeCSVContent(filename, lines);
    }

    /**
     * 解析刚上传的csv文件
     *
     * @param file 刚上传的csv
     */
    public static void analyzeUploadedCSV(MultipartFile file) {
        String filename = file.getOriginalFilename();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        } catch (IOException e) {
            LOGGER.warn(FILE_OPEN_ERROR);
            throw new CSVException(FILE_OPEN_ERROR);
        }
        List<String[]> lines = new LinkedList<>();
        splitCSVLines(reader, lines);
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.warn(FILE_CLOSE_ERROR);
            throw new CSVException(FILE_CLOSE_ERROR);
        }
        analyzeCSVContent(filename, lines);
    }

    /**
     * 将文件流中的数据读入并按分隔符\",\"符切割
     *
     * @param reader 流阅读器
     * @param lines  接收文件内容的可变二维矩阵
     */
    private static void splitCSVLines(BufferedReader reader, List<String[]> lines) {
        String line;
        try {
            line = reader.readLine();
            String[] temp = line.split(COL_SEPARATOR);
            if (temp.length != COL_NUM) {
                throw new RuntimeException();
            }
        } catch (IOException e) {
            LOGGER.error(UNKNOWN_HEADER_ERROR);
            throw new CSVException(UNKNOWN_HEADER_ERROR);
        } catch (RuntimeException e) {
            LOGGER.error(HEADER_FORMAT_ERROR);
            throw new CSVException(HEADER_FORMAT_ERROR);
        }
        for (int i = 2; true; i++) {
            try {
                line = reader.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                String[] parts = line.substring(1, line.length() - 1).split(COL_SEPARATOR);
                Assert.assertEquals(COL_NUM, parts.length);
                lines.add(parts);
            } catch (IOException e) {
                LOGGER.warn(COL_READ_ERROR + i);
                throw new CSVException(COL_READ_ERROR + i);
            } catch (AssertionError e) {
                LOGGER.warn(COL_FORMAT_ERROR + i);
                throw new CSVException(COL_FORMAT_ERROR + i);
            }
        }
    }

    /**
     * 解析csv内容并写入数据库
     *
     * @param filename 文件名
     * @param lines    解析完的文件内容
     */
    private static void analyzeCSVContent(String filename, List<String[]> lines) {
        // 初始化待解析的数据
        List<List<Affiliation>> affiliationList = new LinkedList<>();
        List<List<Author>> authorsList = new LinkedList<>();
        List<Conference> conferenceList = new LinkedList<>();
        List<Paper> paperList = new LinkedList<>();
        List<Publisher> publisherList = new LinkedList<>();
        List<List<Term>> termsList = new LinkedList<>();
        List<Publish> publishList = new LinkedList<>();
        List<PaperTerm> paperTermList = new LinkedList<>();

        /**
         * TODO: 这些是已经存在的固定数据，如作者、机构等，后面有空的话可以用redis来优化
         */
        Map<String, Affiliation> existedAffiliation = new HashMap<>();
        Map<String, Author> existedAuthor = new HashMap<>();
        Map<String, Conference> existedConference = new HashMap<>();
        Map<String, Term> existedTerm = new HashMap<>();
        Map<String, Publisher> existedPublisher = new HashMap<>();
        // 这里的key为paperTitle+年份
        Map<String, Paper> existedPaper = new HashMap<>();

        analyzeAffiliation(lines, affiliationList, existedAffiliation);
        analyzeAuthor(lines, authorsList, affiliationList, existedAuthor);
        analyzeConference(filename, lines, conferenceList, existedConference);
        analyzePublisher(lines, publisherList, existedPublisher);
        analyzeTerm(lines, termsList, existedTerm);
        analyzePaper(lines, paperList, publisherList, conferenceList, existedPaper);

        analyzePublish(paperList, authorsList, publishList);
        analyzePaperTerm(paperList, termsList, paperTermList);

    }

    /**
     * 解析学者所属机构，如有新数据则存入数据库
     *
     * @param lines              csv数据矩阵
     * @param affiliationList    需要存放csv中机构信息的二维链表
     * @param existedAffiliation 已经存在的机构的内存缓存
     */
    private static void analyzeAffiliation(List<String[]> lines, List<List<Affiliation>> affiliationList, Map<String, Affiliation> existedAffiliation) {
        List<Affiliation> newAffiliations = new LinkedList<>();
        for (String[] parts : lines) {
            String[] raw = parts[2].split(";");
            List<Affiliation> affiliations = new LinkedList<>();
            for (String name : raw) {
                name = StringUtil.stripAccents(name.trim());
                String key = name.toLowerCase();
                if (existedAffiliation.containsKey(key)) {
                    affiliations.add(existedAffiliation.get(key));
                } else {
                    Affiliation a = affiliationMapper.selectByName(name);
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

    /**
     * 解析学者信息，如有新数据则存入数据库
     *
     * @param lines           csv数据矩阵
     * @param authorsList     需要存放csv中学者信息的二维链表
     * @param affiliationList csv中的机构信息，是一个二维链表
     * @param existedAuthor   已经存在的学者的内存缓存
     */
    private static void analyzeAuthor(List<String[]> lines, List<List<Author>> authorsList, List<List<Affiliation>> affiliationList, Map<String, Author> existedAuthor) {
        List<Author> newAuthors = new LinkedList<>();
        Iterator<List<Affiliation>> outerItr = affiliationList.iterator();
        for (String[] parts : lines) {
            String[] names = parts[1].split(";");
            List<Author> authors = new LinkedList<>();
            List<Affiliation> affiliations = outerItr.next();
            Iterator<Affiliation> itr = affiliations.iterator();
            for (String name : names) {
                name = StringUtil.stripAccents(name.trim());
                String key = name.toLowerCase();
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

    /**
     * 解析会议信息，如有新数据则存入数据库
     *
     * @param filename          csv文件名，实际上会议信息由文件名获取
     * @param lines             csv数据矩阵
     * @param conferenceList    csv中的会议信息的一维链表
     * @param existedConference 已经存在的会议实体的内存缓存
     */
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

    /**
     * 解析出版社的信息，如有新数据则存入数据库
     *
     * @param lines            csv数据矩阵
     * @param publisherList    需要存放csv中出版社的一维链表
     * @param existedPublisher 已经存在的出版社信息内存缓存
     */
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

    /**
     * 解析术语信息，如有新数据则存入数据库
     *
     * @param lines       csv数据矩阵
     * @param termList    需要存放csv中术语信息的二维链表
     * @param existedTerm 已经存在的术语信息的内存缓存
     */
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

    /**
     * 解析文献信息，如有新数据则存入数据库
     *
     * @param lines          csv数据矩阵
     * @param paperList      需要存放csv中文献信息的二维链表
     * @param publisherList  解析后的出版社信息一维链表
     * @param conferenceList 解析后的会议信息一维链表
     * @param existedPaper   已经存在的文献信息内的存缓存
     */
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

    /**
     * 解析文献和术语的联系，如有新数据则存入数据库
     *
     * @param paperList     解析后的csv中文献信息的二维链表
     * @param termsList     解析后的csv中术语信息的二维链表
     * @param paperTermList 需要存放csv中文献术语联系的一维链表
     */
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

    /**
     * 解析文献和学者的联系，如有新数据则存入数据库
     *
     * @param paperList   解析后的csv中文献信息的二维链表
     * @param authorsList 解析后的csv中学者信息的二维链表
     * @param publishList 需要存放csv中文献与学者联系的一维链表
     */
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

    public static class CSVException extends RuntimeException {
        public CSVException(String msg) {
            super(msg);
        }
    }

}
