package group.iiicestseb.backend.utils;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

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
        analyzeExistedJson("E:\\codes\\backend\\src\\main\\resources\\json\\Standard.json");
    }

    public static void analyzeExistedJson(String filename) {
        List<String> lines = new LinkedList<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            ReadLines(lines, br);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println();
    }

    private static void ReadLines(List<String> lines, BufferedReader br) {
        String line;
        while (true) {
            try {
                line = br.readLine();
            } catch (IOException e) {
                System.out.println();
                continue;
            }
            if (line == null || line.isEmpty()) {
                break;
            }
            lines.add(line);
        }
    }

    public static class JSONException extends RuntimeException {
        public JSONException(String msg) {
            super(msg);
        }
    }

}
