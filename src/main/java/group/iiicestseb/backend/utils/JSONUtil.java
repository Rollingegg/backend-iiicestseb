package group.iiicestseb.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jh
 * @date 2020/3/18
 */
public class JSONUtil {


    /**
     * 这是JSON中规定的关键词
     * ArticleId("articleId"),
     * Abstract("abstract"),
     * Title("title"),
     * ConfLoc("confLoc"),
     * IssueLink("issueLink"),
     * PdfLink("pdfLink"),
     * Ref("ref"),
     * ChronDate("chronDate"),
     * Publication("publication"),
     * DOI("doi"),
     * Authors("authors"),
     * Keywords("keywords"),
     * StartPage("startPage"),
     * EndPage("endPage"),
     * Metrics("metrics"),
     * Publisher("publisher");
     */
    public enum KEY {
        //
        ArticleId("articleId"),
        Abstract("abstract"),
        Title("title"),
        ConfLoc("confLoc"),
        IssueLink("issueLink"),
        PdfLink("pdfLink"),
        Ref("ref"),
        ChronDate("chronDate"),
        Publication("publication"),
        DOI("doi"),
        Authors("authors"),
        Keywords("keywords"),
        StartPage("startPage"),
        EndPage("endPage"),
        Metrics("metrics"),
        Publisher("publisher");
        private final String value;

        KEY(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 这是Metrics中的几个关键词
     * TotalDownloads("totalDownloads"),
     * CitationCountPaper("citationCountPaper"),
     * CitationCountPatent("citationCountPatent");
     */
    public enum METRICS {
        //
        TotalDownloads("totalDownloads"),
        CitationCountPaper("citationCountPaper"),
        CitationCountPatent("citationCountPatent");
        public final String value;

        METRICS(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 这是文章关键词中的关键词
     * IEEE("IEEE"),
     * Ctrl(""),
     * NonCtrl(""),
     * Author("authorKeywords"),
     * Mesh("");
     */
    public enum KEYWORD {
        //
        IEEE("IEEE"),
        Ctrl(""),
        NonCtrl(""),
        Author("authorKeywords"),
        Mesh("");
        public final String value;

        KEYWORD(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    public static void main(String[] args) {
        analyzeJson("E:\\codes\\backend\\src\\main\\resources\\json\\Standard.json");
    }

    public static void analyzeJson(String filename) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            String s = br.readLine();
            JSONObject o1 = (JSONObject) JSON.parse(s);
            s = br.readLine();
            JSONObject o2 = (JSONObject) JSON.parse(s);
            s = br.readLine();
            JSONObject o3 = (JSONObject) JSON.parse(s);
            s = br.readLine();
            JSONObject o4 = (JSONObject) JSON.parse(s);
            String a = KEY.Abstract.value;
            a = KEY.ArticleId.value;
            System.out.println();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class JSONException extends RuntimeException {
        public JSONException(String msg) {
            super(msg);
        }
    }

}
