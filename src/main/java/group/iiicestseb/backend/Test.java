package group.iiicestseb.backend;

import group.iiicestseb.backend.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;

/**
 * @author jh
 */
public class Test {
    public static void main(String[] args) throws Exception {
        test();
//        AnalyzeCSV("icse.csv");
        Class<?>[] cs = {String.class, Integer.class, LinkedList.class};
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Test.class);
    private static final Map<String, Integer> STANDARDS = new HashMap<String, Integer>() {
        {
            put("IEEE Terms", 1);
            put("INSPEC Controlled Terms", 2);
            put("INSPEC Non-Controlled Terms", 3);
            put("Mesh Terms", 4);
        }
    };

    public static void test() {
        File file = new File("test.csv");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found" + file.getName());
            return;
        }

        // TODO: 这些是已经存在的固定数据，如作者、机构等，后面有空的话可以用redis来优化
        Map<String, Affiliation> existedAffiliation = new HashMap<>();
        Map<String, Author> existedAuthor = new HashMap<>();
        Map<String, Conference> existedConference = new HashMap<>();
        // 这里term的key值为 word + standard_id(硬编码)
        Map<String, Term> existedTerm = new HashMap<>();
        Map<String, Publisher> existedPublisher = new HashMap<>();

        // TODO: 重复论文也需要排除，不过这个后面有空再做吧

        // 初始化待解析的数据
        List<List<Affiliation>> affiliationList = new LinkedList<>();
        List<List<Author>> authorsList = new LinkedList<>();
        List<Conference> conferenceList = new LinkedList<>();
        List<Paper> paperList = new LinkedList<>();
        List<List<PaperTerm>> paperTermsList = new LinkedList<>();
        List<List<Publish>> publishesList = new LinkedList<>();
        List<Publisher> publisherList = new LinkedList<>();
        List<TermStandard> termStandardList = new LinkedList<>();
        List<List<Term>> termsList = new LinkedList<>();

        // 读取csv
        String line;
        List<String[]> lines = new LinkedList<>();
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

        // TODO: 解析lines
        getAffiliation(lines, affiliationList, existedAffiliation);

        // TODO: 将上面的数据全部写入数据库


    }

    private static void getAffiliation(List<String[]> lines, List<List<Affiliation>> affiliationList, Map<String, Affiliation> existedAffiliation){
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

    private static void getAuthor(List<String[]> lines, List<List<Author>> authorsList, List<List<Affiliation>> affiliationList, Map<String, Author> existedAuthor){
        for (String[] parts : lines) {
            String[] names = parts[1].split("; ");
            Iterator<List<Affiliation>> outerItr = affiliationList.iterator();
            List<Author> authors = new LinkedList<>();
            for (String name : names) {
                List<Affiliation> affiliations = outerItr.next();
                Iterator<Affiliation> itr = affiliations.iterator();
                if (existedAuthor.containsKey(name)) {
                    authors.add(existedAuthor.get(name));
                } else {
                    // 这里访问一下数据库看是否存在
//                Author a = select from db;
                    //if (a != null){existed.add; list.add}
                    //else
                    Author a = new Author(name, itr.next().getId());
                    existedAuthor.put(name, a);
                    authors.add(a);
                }
            }
            authorsList.add(authors);
        }
        //insert into db;
    }

    private static void getConference(String[] parts, List<Conference> conferenceList, Map<String, Conference> existedConference){

    }

    private static void getPublisher(String[] parts, List<Publisher> publisherList, Map<String, Publisher> existedPublisher){

    }

    private static void getTermStandard(String[] parts, List<TermStandard> termList, Map<String, Term> existedTerm){
        // 这个好像可以不用写，估计要硬编码了
    }

    private static void getTerm(String[] parts, List<List<Term>> termList, Map<String, Term> existedTerm){

    }


    public static void test2() throws Exception {
        Class<?> clz = String.class;
        Constructor<?> m = clz.getConstructor(String.class);
        Object o = m.newInstance("123");
        String s = (String) o;
        System.out.println(s);

    }

    public static void AnalyzeCSV(String filename) {
        List<String> document_titles = new LinkedList<>();
        List<List<String>> authors = new LinkedList<>();
        List<List<String>> author_affiliations = new LinkedList<>();
        List<String> publication_title = new LinkedList<>();
        List<String> date_add_to_xplore = new LinkedList<>();//这一列好像都为空
        List<Date> publication_year = new LinkedList<>();
        List<String> volume = new LinkedList<>();
        List<String> issues = new LinkedList<>();
        List<Integer> start_page = new LinkedList<>();
        List<Integer> end_page = new LinkedList<>();
        List<String> abstracts = new LinkedList<>();
        List<String> ISSN = new LinkedList<>();
        List<String> ISBN = new LinkedList<>();
        List<String> DOI = new LinkedList<>();
        List<String> funding_information = new LinkedList<>();
        List<String> PDF_link = new LinkedList<>();
        List<List<String>> author_keywords = new LinkedList<>();
        List<List<String>> IEEE_terms = new LinkedList<>();
        List<List<String>> INSPEC_controlled_terms = new LinkedList<>();
        List<List<String>> INSPEC_non_controlled_terms = new LinkedList<>();
        List<List<String>> Mesh_terms = new LinkedList<>();
        List<Integer> article_citation_Count = new LinkedList<>();
        List<Integer> reference_count = new LinkedList<>();
        List<String> license = new LinkedList<>();
        List<String> online_date = new LinkedList<>();
        List<String> issue_date = new LinkedList<>();
        List<String> meeting_date = new LinkedList<>();
        List<String> publisher = new LinkedList<>();
        List<String> document_identifier = new LinkedList<>();
    }
}
