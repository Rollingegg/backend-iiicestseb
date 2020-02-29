package group.iiicestseb.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jh
 */
public class Test {
    public static void main(String[] args) throws Exception {
        test();
//        AnalyzeCSV("icse.csv");
        Class<?>[] cs = {String.class, Integer.class, LinkedList.class};
    }


    public static void test() throws Exception {
        File f = new File("test.csv");
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
        String[] ss = (reader.readLine().split("\",\""));
        ss[0] = ss[0].substring(1);
        ss[ss.length - 1] = ss[ss.length - 1].substring(0, ss[ss.length - 1].length() - 1);
        reader.close();
        System.out.println(ss.length);
        System.out.println();
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
