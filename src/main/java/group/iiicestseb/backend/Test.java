
package group.iiicestseb.backend;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;


/**
 * @author jh
 */
public class Test {
    public static void main(String[] args) throws Exception {
        test("test.csv");
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
//    private static final Map<String, Integer> STANDARDS = new HashMap<String, Integer>() {
//        {
//            put("IEEE Terms", 1);
//            put("INSPEC Controlled Terms", 2);
//            put("INSPEC Non-Controlled Terms", 3);
//            put("Mesh Terms", 4);
//        }
//    };

    public static void test(String filename) {
        List<String[]> lines = new LinkedList<>();
        AnalyzeCSVLines(filename, lines);

        // TODO: 这些是已经存在的固定数据，如作者、机构等，后面有空的话可以用redis来优化
        Map<String, Affiliation> existedAffiliation = new HashMap<>();
        Map<String, Author> existedAuthor = new HashMap<>();
        Map<String, Conference> existedConference = new HashMap<>();
        Map<String, Term> existedTerm = new HashMap<>();
        Map<String, Publisher> existedPublisher = new HashMap<>();
        Map<String, Paper> existedPaper = new HashMap<>();

        // 初始化待解析的数据
        List<List<Affiliation>> affiliationList = new LinkedList<>();
        List<List<Author>> authorsList = new LinkedList<>();
        List<Conference> conferenceList = new LinkedList<>();
        List<Paper> paperList = new LinkedList<>();
        List<Publisher> publisherList = new LinkedList<>();
        List<List<Term>> termsList = new LinkedList<>();


        // TODO: 解析lines
        AnalyzeAffiliation(lines, affiliationList, existedAffiliation);
        AnalyzeAuthor(lines, authorsList, affiliationList, existedAuthor);
        AnalyzeConference(filename, lines, conferenceList, existedConference);
        AnalyzePublisher(lines, publisherList, existedPublisher);
        AnalyzeTerm(lines, termsList, existedTerm);
        AnalyzePaper(lines, paperList, publisherList, conferenceList, existedPaper);
        // TODO: 将上面的数据全部写入数据库


        System.out.println();

    }

    private static void AnalyzeCSVLines(String filename, List<String[]> lines) {
        File file = new File(filename);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found" + file.getName());
            return;
        }
        String line;
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

    private static void AnalyzeAffiliation(List<String[]> lines, List<List<Affiliation>> affiliationList, Map<String, Affiliation> existedAffiliation) {
        List<Affiliation> newAffiliations = new LinkedList<>();
        for (String[] parts : lines) {
            String[] raw = parts[2].split("; ");
            List<Affiliation> affiliations = new LinkedList<>();
            for (String name : raw) {
                if (existedAffiliation.containsKey(name)) {
                    affiliations.add(existedAffiliation.get(name));
                } else {
                    // 这里访问一下数据库看是否存在
//                Affiliation a = select from db;
                    //if (a != null){existedA.add; list.add}
                    //else
                    Affiliation a = new Affiliation(name);
                    newAffiliations.add(a);
                    existedAffiliation.put(name, a);
                    affiliations.add(a);
                    //insert into db;
                }
            }
            affiliationList.add(affiliations);
        }
        //insert into db newList;
    }

    private static void AnalyzeAuthor(List<String[]> lines, List<List<Author>> authorsList, List<List<Affiliation>> affiliationList, Map<String, Author> existedAuthor) {
        List<Author> newAuthors = new LinkedList<>();
        Iterator<List<Affiliation>> outerItr = affiliationList.iterator();
        for (String[] parts : lines) {
            String[] names = parts[1].split("; ");
            List<Author> authors = new LinkedList<>();
            List<Affiliation> affiliations = outerItr.next();
            Iterator<Affiliation> itr = affiliations.iterator();
            for (String name : names) {
                if (existedAuthor.containsKey(name)) {
                    authors.add(existedAuthor.get(name));
                } else {
                    // 这里访问一下数据库看是否存在
//                Author a = select from db;
                    //if (a != null){existed.add; list.add}
                    //else
                    Author a = new Author(name, itr.next().getId());
                    newAuthors.add(a);
                    existedAuthor.put(name, a);
                    authors.add(a);
                }
            }
            authorsList.add(authors);
        }
        //insert into db;
    }

    private static void AnalyzeConference(String filename, List<String[]> lines, List<Conference> conferenceList, Map<String, Conference> existedConference) {
        List<Conference> newConference = new LinkedList<>();
        Conference conference;
        String name = filename.split("\\.")[0];
        if (existedConference.containsKey(name)) {
            conference = existedConference.get(name);
        } else {
//            c = select from db;
            //if (c != null){existed.add; list.add}
            //else
            conference = new Conference(name);
            existedConference.put(name, conference);
            newConference.add(conference);
        }
        for (String[] ignored : lines) {
            conferenceList.add(conference);
        }
        //insert into db;
    }

    private static void AnalyzePublisher(List<String[]> lines, List<Publisher> publisherList, Map<String, Publisher> existedPublisher) {
        List<Publisher> newPublisher = new LinkedList<>();
        for (String[] parts : lines) {
            String name = parts[27];
            if (existedPublisher.containsKey(name)) {
                publisherList.add(existedPublisher.get(name));
            } else {
                //p = select from db;
                //if (p != null){existed.add; list.add}
                //else
                Publisher p = new Publisher(name);
                existedPublisher.put(name, p);
                newPublisher.add(p);
                publisherList.add(p);
            }
        }
        //insert into db;
    }

    private static void AnalyzeTerm(List<String[]> lines, List<List<Term>> termList, Map<String, Term> existedTerm) {
        List<Term> newTerms = new LinkedList<>();
        for (String[] parts : lines) {
            List<Term> terms = new LinkedList<>();
            for (int i : new int[]{17, 18, 19, 20}) {
                String[] names = parts[i].split(";");
                if (names[0].isEmpty()) {
                    continue;
                }
                for (String name : names) {
                    if (existedTerm.containsKey(name)) {
                        terms.add(existedTerm.get(name));
                    } else {
                        // 这里访问一下数据库看是否存在
//                Term t = select from db;
                        //if (t != null){existed.add; list.add}
                        //else
                        Term a = new Term(name);
                        newTerms.add(a);
                        existedTerm.put(name, a);
                        terms.add(a);
                    }
                }
            }
            termList.add(terms);
        }
        //insert into db newList;
    }

    private static void AnalyzePaper(List<String[]> lines, List<Paper> paperList, List<Publisher> publisherList, List<Conference> conferenceList, Map<String, Paper> existedPaper) {
        List<Paper> newPaper = new LinkedList<>();
        Iterator<Publisher> publisherItr = publisherList.iterator();
        Iterator<Conference> conferenceItr = conferenceList.iterator();
        for (String[] parts : lines) {
            String paperTitle = parts[0];
            if (existedPaper.containsKey(paperTitle)) {
                paperList.add(existedPaper.get(paperTitle));
            } else {
                //p = select from db;
                //if (p != null){existed.add; list.add}
                //else
                Paper p = new Paper();
                p.setPaperTitle(paperTitle);
                p.setPublicationTitle(parts[3]);
                p.setPublicationYear(DateUtil.parseYear(parts[5]));
                p.setStartPage(Integer.parseInt(parts[8]));
                p.setEndPage(Integer.parseInt(parts[9]));
                p.setPaperAbstract(parts[10]);
                p.setDoi(parts[13]);
                p.setPdfLink(parts[15]);
                p.setAuthorKeywords(parts[16]);
                p.setCitationCount(parts[21].isEmpty() ? 0 : Integer.parseInt(parts[21]));
                p.setReferenceCount(parts[22].isEmpty() ? 0 : Integer.parseInt(parts[22]));
                p.setPublisherId(publisherItr.next().getId());
                p.setConferenceId(conferenceItr.next().getId());
                p.setDocumentIdentifier(parts[28]);
                existedPaper.put(paperTitle, p);
                newPaper.add(p);
                paperList.add(p);
            }
        }
        //insert into db;
    }

}

