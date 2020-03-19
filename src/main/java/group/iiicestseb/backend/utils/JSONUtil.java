package group.iiicestseb.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import group.iiicestseb.backend.Test;
import group.iiicestseb.backend.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author jh
 * @date 2020/3/18
 */
public class JSONUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);

    public static final String LINE_READ_ERROR = "文件内容读取错误，跳过该行，请检查格式，错误行号：";
    public static final String LINE_PARSE_ERROR = "JSON内容解析失败，跳过该行，请检查格式，错误行号：";
    public static final String INPUT_STREAM_CLOSE_ERROR_KEY = "Stream_Error";
    public static final String INPUT_STREAM_CLOSE_ERROR = "文件流关闭失败，停止解析";
    public static final String FILE_OPEN_ERROR = "文件流打开失败";

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
        analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\Standard.json");
    }

    public static void analyzeExistedJsonFile(String filename) {
        List<JSONObject> jsonObjects = new LinkedList<>();
        JSONObject errors = new JSONObject();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filename))));
            ReadLines(jsonObjects, br, errors);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
            errors.put(INPUT_STREAM_CLOSE_ERROR_KEY, INPUT_STREAM_CLOSE_ERROR);
            return;
        }
        analyze(jsonObjects, errors);
        System.out.println();
    }

    public static void analyzeUploadedJsonFile(MultipartFile file) {
    }

    private static void ReadLines(List<JSONObject> jsonObjects, BufferedReader br, JSONObject errors) {
        String line;
        for (int i = 1; true; i++) {
            try {
                line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                JSONObject o = (JSONObject) JSON.parse(line);
                o.put("line", i);
                jsonObjects.add(o);
            } catch (IOException e) {
                e.printStackTrace();
                errors.put("line" + i, LINE_READ_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                errors.put("line" + i, LINE_PARSE_ERROR);
            }

        }
    }

    private static void analyze(List<JSONObject> jsonObjects, JSONObject errors) {
        JSONObject ExistedMaps = new JSONObject();
        ExistedMaps.put("existedConference", new HashMap<String, Conference>());
        ExistedMaps.put("existedTerm", new HashMap<String, Term>());
        ExistedMaps.put("existedAffiliation", new HashMap<String, Affiliation>());
        ExistedMaps.put("existedAuthor", new HashMap<String, Author>());
        ExistedMaps.put("existedPaper", new HashMap<String, Paper>());

        for (JSONObject jo : jsonObjects) {
            // 会议
            Conference conference = analyzeConference(jo);
            // 关键词
            List<Term> termList = analyzeTerms(jo);
            // 机构
            // 作者
            // 文献
            // 文献-关键词
            // 文献-作者
        }
    }

    private static Conference analyzeConference(JSONObject jo) {
        return null;
    }

    private static List<Term> analyzeTerms(JSONObject jo) {
        return null;
    }

    public static class JSONException extends RuntimeException {
        public JSONException(String msg) {
            super(msg);
        }
    }

}
