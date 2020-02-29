package group.iiicestseb.backend;

import group.iiicestseb.backend.entity.*;
import jdk.internal.util.xml.impl.Pair;
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
            put("Mesh_Terms", 4);
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

        // 这些是已经存在的固定数据，如作者、机构等
        Map<String, Affiliation> existedAffiliations = new HashMap<>();
        Map<String, Author> existedAuthors = new HashMap<>();
        Map<String, Conference> existedConference = new HashMap<>();
        // 这里term的key值为 word + standard_id(硬编码)
        Map<String, Term> existedTerm = new HashMap<>();


        // 这些是需要新存入数据库的数据
        List<List<Affiliation>> affiliationList = new LinkedList<>();
        List<List<Author>> authorsList = new LinkedList<>();
        List<Conference> conferenceList = new LinkedList<>();
        List<Paper> paperList = new LinkedList<>();
        List<List<PaperTerm>> paperTermsList = new LinkedList<>();
        List<List<Publish>> publishesList = new LinkedList<>();
        List<Publisher> publisherList = new LinkedList<>();
        List<TermStandard> termStandardList = new LinkedList<>();
        List<List<Term>> termsList = new LinkedList<>();

        String line;
        for (int i = 0; true; i++) {
            try {
                line = reader.readLine();
                if (line.isEmpty()) {
                    break;
                }
                String[] parts = line.substring(1, line.length() - 1).split("\",\"");
                assert parts.length == 29;
                Paper paper = new Paper();
            } catch (IOException e) {
                LOGGER.warn("第" + i + "行解析出错，跳过该行");
            }
        }
        try {
            reader.close();
        } catch (IOException e) {
            LOGGER.warn("解析csv结束，但是出了未知错误");
        }
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
