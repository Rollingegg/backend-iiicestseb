package group.iiicestseb.backend.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.exception.paper.JSONAnalyzeException;
import group.iiicestseb.backend.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;


/**
 * @author jh
 * @date 2020/3/18
 */
@Component("JSONUtil")
public class JSONUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONUtil.class);

    public static final String LINE_READ_ERROR = "文件内容读取错误，跳过该行，请检查格式";
    public static final String LINE_PARSE_ERROR = "JSON内容转换失败，跳过该行，请检查格式";
    public static final String JSON_PARSE_ERROR = "JSON内容解析失败，跳过该行，请检查格式";
    public static final String INPUT_STREAM_CLOSE_ERROR_KEY = "Stream_Error";
    public static final String INPUT_STREAM_CLOSE_ERROR = "文件流关闭失败，停止解析";
    public static final String FILE_OPEN_ERROR = "文件流打开失败";
    public static final String FILE_NOT_FOUND = "文件不存在：";
    public static final String CONFERENCE_NOT_EXIST = "会议名不存在";
    public static final String PAPER_EXISTED = "文献已存在，跳过该行";
    public static final String ANALYZE_COUNT_INFO = "共解析这么多行数据：";
    public static final String SUCCESS_COUNT_INFO = "成功：";
    public static final String EXISTED_COUNT_INFO = "已存在：";
    public static final String ERROR_COUNT_INFO = "请查看日志，发生未知错误数：";
    public static final String STANDARD_JSON_FILE = "Standard.json";
    public static final String GET_STANDARD_JSON_FILE_ERROR = "获取标准json文件模板失败，请查看日志";

    @Resource(name = "Regedit")
    private PaperManageService paperManageService;
    @Resource(name = "Regedit")
    private AuthorService authorService;
    @Resource(name = "Regedit")
    private AffiliationService affiliationService;
    @Resource(name = "Regedit")
    private ConferenceService conferenceService;

    private static JSONUtil Instance;

    @PostConstruct
    public void init() {
        JSONUtil.Instance = this;
    }

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
        PubLink("pubLink"),
        IssueLink("issueLink"),
        PdfLink("pdfLink"),
        Ref("ref"),
        ChronDate("chronDate"),
        PublicationTitle("publication"),
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
     * 会议名
     * ASE("ASE"),
     * ICSE("ICSE");
     */
    public enum CONFERENCE {
        //
        ASE("ASE"),
        ICSE("ICSE");
        public final String value;

        CONFERENCE(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 文章关键词Term
     * IEEE("IEEE Keywords"),
     * Ctrl("INSPEC: Controlled Indexing"),
     * NonCtrl("INSPEC: Non-Controlled Indexing"),
     * Author("Author Keywords"),
     * Index("Index");
     */
    public enum TERM {
        //
        IEEE("IEEE Keywords"),
        Ctrl("INSPEC: Controlled Indexing"),
        NonCtrl("INSPEC: Non-Controlled Indexing"),
        Author("Author Keywords"),
        Index("Index");
        public final String value;

        TERM(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 作者
     * Name("name"),
     * FirstName("firstName"),
     * LastName("lastName"),
     * Affiliation("affiliation");
     */
    public enum AUTHOR {
        //
        Name("name"),
        FirstName("firstName"),
        LastName("lastName"),
        Affiliation("affiliation");
        public final String value;

        AUTHOR(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 引用
     * Order("order"),
     * Text("text"),
     * Title("title"),
     * GoogleScholarLink("googleScholarLink"),
     * RefType("refType");
     */
    public enum REF {
        //
        Order("order"),
        Text("text"),
        Title("title"),
        GoogleScholarLink("googleScholarLink"),
        RefType("refType");
        public final String value;

        REF(String value) {
            this.value = value;
        }

        public String value() {
            return this.value;
        }
    }

    /**
     * 加载测试数据
     */
    public static void loadTestData() {
        analyzeExistedJsonFile(STANDARD_JSON_FILE);
        Instance.paperManageService.reComputePapersScore();
    }

    /**
     * 解析已经存在的数据源
     *
     * @param filename 数据源文件名
     * @return 解析日志
     */
    public static JSONObject analyzeExistedJsonFile(String filename) {
        return Instance.analyzeExistedJsonFileInner(filename);
    }

    private JSONObject analyzeExistedJsonFileInner(String filename) {
        ClassPathResource file = new ClassPathResource("json/" + filename);
        List<JSONObject> jsonObjects = new LinkedList<>();
        List<String> logs = new LinkedList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            ReadLines(jsonObjects, br, logs);
            br.close();
        } catch (FileNotFoundException e) {
            throw new JSONAnalyzeException(FILE_NOT_FOUND + filename);
        } catch (IOException e) {
            throw new JSONAnalyzeException(INPUT_STREAM_CLOSE_ERROR);
        }
        return analyze(jsonObjects, logs);
    }

    /**
     * 解析上传的数据源
     *
     * @param file 上传的数据源文件
     * @return 解析日志
     */
    public static JSONObject analyzeUploadedJsonFile(MultipartFile file) {
        return Instance.analyzeUploadedJsonFileInner(file);
    }

    private JSONObject analyzeUploadedJsonFileInner(MultipartFile file) {
        List<JSONObject> jsonObjects = new LinkedList<>();
        List<String> logs = new LinkedList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(file.getInputStream()));
            ReadLines(jsonObjects, br, logs);
            br.close();
        } catch (IOException e) {
            throw new JSONAnalyzeException(INPUT_STREAM_CLOSE_ERROR);
        }
        return analyze(jsonObjects, logs);
    }

    /**
     * 获取JSON模板
     *
     * @param response 请求的回答
     */
    public static void getStandardJSON(HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream; charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Standard.json";
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/octet-stream");
        ClassPathResource file = new ClassPathResource("json/Standard.json");
        try {
            response.getOutputStream().write(file.getInputStream().readAllBytes());
            response.setContentLength(Math.toIntExact(file.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
            throw new JSONAnalyzeException(GET_STANDARD_JSON_FILE_ERROR);
        }
    }

    private void ReadLines(List<JSONObject> jsonObjects, BufferedReader br, List<String> logs) {
        String line;
        for (int i = 1; true; i++) {
            try {
                line = br.readLine();
                if (line == null || line.isEmpty()) {
                    break;
                }
                JSONObject o = (JSONObject) JSON.parse(line);
                o.put("line", "第" + i + "行：");
                jsonObjects.add(o);
            } catch (IOException e) {
                e.printStackTrace();
                logs.add("第" + i + "行：" + LINE_READ_ERROR);
                LOGGER.warn("第" + i + "行：" + LINE_READ_ERROR);
            } catch (Exception e) {
                e.printStackTrace();
                logs.add("第" + i + "行：" + LINE_PARSE_ERROR);
                LOGGER.warn("第" + i + "行：" + LINE_PARSE_ERROR);
            }
        }
    }

    private JSONObject analyze(List<JSONObject> jsonObjects, List<String> logs) {
        JSONObject result = new JSONObject();
        Map<Class<?>, Map<?, ?>> ExistedMaps = new HashMap<>();
        ExistedMaps.put(Conference.class, new HashMap<String, Conference>());
        ExistedMaps.put(Term.class, new HashMap<String, Term>());
        ExistedMaps.put(Affiliation.class, new HashMap<String, Affiliation>());
        ExistedMaps.put(Author.class, new HashMap<String, Author>());
        ExistedMaps.put(Paper.class, new HashMap<Integer, Paper>());

        Map<Class<?>, List<?>> NewLists = new HashMap<>();
        NewLists.put(Conference.class, new LinkedList<Conference>());
        NewLists.put(Term.class, new LinkedList<Term>());
        NewLists.put(Affiliation.class, new LinkedList<Affiliation>());
        NewLists.put(Author.class, new LinkedList<Author>());
        NewLists.put(Paper.class, new LinkedList<Paper>());

        int count = 0;
        int success = 0;
        int existed = 0;
        int error = 0;
        for (JSONObject jo : jsonObjects) {
            count++;
            try {
                analyzeOne(jo, ExistedMaps, NewLists);
                success++;
            } catch (JSONAnalyzeException e) {
                LOGGER.warn(jo.getString("line") + e.getMessage());
                logs.add(jo.getString("line") + e.getMessage());
                existed++;
            } catch (NumberFormatException | NullPointerException e) {
                LOGGER.warn(jo.getString("line") + JSON_PARSE_ERROR);
                logs.add(jo.getString("line") + JSON_PARSE_ERROR);
                error++;
            } catch (Exception e) {
                LOGGER.warn(jo.getString("line") + e.getMessage());
                logs.add(jo.getString("line") + e.getMessage());
                error++;
                e.printStackTrace();
            }
        }
        LOGGER.info(ANALYZE_COUNT_INFO + count);
        LOGGER.info(SUCCESS_COUNT_INFO + success);
        LOGGER.info(EXISTED_COUNT_INFO + existed);
        LOGGER.info(ERROR_COUNT_INFO + error);

        result.put("totalCount", count);
        result.put("successCount", success);
        result.put("existedCount", existed);
        result.put("errorCount", error);
        result.put("errorLogs", logs);
        result.put("papers", NewLists.get(Paper.class));
        return result;
    }

    @Transactional(rollbackFor = {JSONAnalyzeException.class, Exception.class})
    public void analyzeOne(JSONObject jo, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) throws JSONAnalyzeException {
        Conference conference = analyzeConference(jo, existedMaps, newLists);
        Paper paper = analyzePaper(jo, existedMaps, newLists);
        paper.setConferenceId(conference.getId());
        // 关键词
        Map<String, List<Term>> terms = analyzeTerms(jo, existedMaps, newLists);
        paper.setAuthorKeywords(joinAuthorKeywords(terms.get(TERM.Author.value())));
        paperManageService.insertPaper(paper);
        // 作者+机构
        List<Author> authors = analyzeAuthors(jo, existedMaps, newLists);
        // 文献-引用
        List<Reference> references = analyzeReference(paper, jo);
        paperManageService.insertReferences(references);
        // 文献-关键词
        List<PaperTerm> paperTerms = analyzePaperTerms(paper, terms.get(TERM.Index.value()));
        paperManageService.insertPaperTermList(paperTerms);
        // 文献-作者
        List<PaperAuthors> paperAuthors = analyzePaperAuthors(paper, authors);
        paperManageService.insertPaperAuthorList(paperAuthors);
    }

    @SuppressWarnings("unchecked")
    private Paper analyzePaper(JSONObject jo, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) {
        Paper paper;
        Integer articleId = jo.getInteger(KEY.ArticleId.value());
        Map<Integer, Paper> existed = (Map<Integer, Paper>) existedMaps.get(Paper.class);
        List<Paper> news = (List<Paper>) newLists.get(Paper.class);
        if (existed.containsKey(articleId)) {
            throw new JSONAnalyzeException(PAPER_EXISTED);
        }
        paper = paperManageService.findPaperByArticleId(articleId);
        if (paper != null) {
            existed.put(articleId, paper);
            throw new JSONAnalyzeException(PAPER_EXISTED);
        }
        paper = new Paper();
        paper.setPdfUrl(jo.getString(KEY.PdfLink.value()));
        paper.setTitle(jo.getString(KEY.Title.value()));
        paper.setPaperAbstract(jo.getString(KEY.Abstract.value()));
        paper.setDoi(jo.getString(KEY.DOI.value()));
        paper.setPublicationTitle(jo.getString(KEY.PublicationTitle.value()));
        Map<String, Integer> Metrics = (Map<String, Integer>) jo.get(KEY.Metrics.value());
        paper.setCitationCountPaper(Metrics.get(METRICS.CitationCountPaper.value()));
        paper.setCitationCountPatent(Metrics.get(METRICS.CitationCountPatent.value()));
        paper.setTotalDownloads(Metrics.get(METRICS.TotalDownloads.value()));
        paper.setStartPage(jo.getString(KEY.StartPage.value()));
        paper.setEndPage(jo.getString(KEY.EndPage.value()));
        paper.setPubLink(jo.getString(KEY.PubLink.value()));
        paper.setIssueLink(jo.getString(KEY.IssueLink.value()));
        paper.setPublisher(jo.getString(KEY.Publisher.value()));
        paper.setConfLoc(jo.getString(KEY.ConfLoc.value()));
        // todo : chronDate的时间格式在json中不同，等赖总爬其他的
        paper.setChronDate(DateUtil.parseChron(jo.getString(KEY.ChronDate.value())));
        paper.setArticleId(articleId);
        news.add(paper);
        return paper;
    }

    @SuppressWarnings("unchecked")
    private Conference analyzeConference(JSONObject jo, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) {
        Conference c;
        Map<String, Conference> existed = (Map<String, Conference>) existedMaps.get(Conference.class);
        List<Conference> news = (LinkedList<Conference>) newLists.get(Conference.class);
        String name = StringUtil.stripAccents(jo.getString(KEY.PublicationTitle.value()).trim());
        String substring = name.substring(name.length() - 7).toUpperCase();
        if (substring.contains(CONFERENCE.ASE.value())) {
            name = CONFERENCE.ASE.value();
        } else if (substring.contains(CONFERENCE.ICSE.value())) {
            name = CONFERENCE.ICSE.value();
        } else {
            //todo: 等赖总加商会议名
            name = Math.random() > 0.5 ? CONFERENCE.ICSE.value() : CONFERENCE.ASE.value();
//            throw new JSONException(CONFERENCE_NOT_EXIST);
        }
        if (existed.containsKey(name)) {
            return existed.get(name);
        }
        if ((c = conferenceService.findConferenceByName(name)) == null) {
            c = new Conference();
            c.setName(name);
            conferenceService.insertConference(c);
            news.add(c);
        }
        existed.put(name, c);
        return c;
    }

    @SuppressWarnings("unchecked")
    private List<Author> analyzeAuthors(JSONObject jo, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) {
        Map<String, Author> existed = (Map<String, Author>) existedMaps.get(Author.class);
        List<Author> news = (List<Author>) newLists.get(Author.class);
        List<Author> tempNews = new LinkedList<>();
        List<Author> authors = new LinkedList<>();
        JSONArray authorArray = jo.getJSONArray(KEY.Authors.value());
        for (Object o : authorArray) {
            Author a;
            JSONObject temp = (JSONObject) o;
            String name = StringUtil.stripAccents(temp.getString(AUTHOR.Name.value()));
            String name_key = name.toLowerCase();
            if (existed.containsKey(name_key)) {
                authors.add(existed.get(name_key));
                continue;
            } else if ((a = authorService.findAuthorByName(name)) == null) {
                String firstName = StringUtil.stripAccents(temp.getString(AUTHOR.FirstName.value()));
                String lastName = StringUtil.stripAccents(temp.getString(AUTHOR.LastName.value()));
                a = new Author();
                a.setName(name);
                a.setFirstName(firstName);
                a.setLastName(lastName);
                Affiliation aff = analyzeAffiliation(temp.getString(AUTHOR.Affiliation.value()), existedMaps, newLists);
                a.setAffiliationId(aff.getId());
                tempNews.add(a);
            }
            authors.add(a);
            existed.put(name_key, a);
        }
        authorService.insertAuthorList(tempNews);
        news.addAll(tempNews);
        return authors;
    }

    @SuppressWarnings("unchecked")
    private Affiliation analyzeAffiliation(String name, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) {
        Affiliation a;
        Map<String, Affiliation> existed = (Map<String, Affiliation>) existedMaps.get(Affiliation.class);
        List<Affiliation> news = (List<Affiliation>) newLists.get(Affiliation.class);
        name = StringUtil.stripAccents(name.trim());
        String name_key = name.toLowerCase();
        if (existed.containsKey(name_key)) {
            return existed.get(name_key);
        }
        if ((a = affiliationService.findAffiliationByName(name)) == null) {
            a = new Affiliation();
            a.setName(name);
            affiliationService.saveAffiliation(a);
            news.add(a);
        }
        existed.put(name_key, a);
        return a;
    }

    @SuppressWarnings("unchecked")
    private Map<String, List<Term>> analyzeTerms(JSONObject jo, Map<Class<?>, Map<?, ?>> existedMaps, Map<Class<?>, List<?>> newLists) {
        List<Term> indexTerms = new LinkedList<>();
        List<Term> authorKeywords = new LinkedList<>();
        Map<String, List<Term>> terms = new HashMap<>() {{
            put(TERM.Index.value(), indexTerms);
            put(TERM.Author.value(), authorKeywords);
        }};
        terms.put(TERM.Index.value(), indexTerms);
        Map<String, Integer> foundTerms = new HashMap<>();
        Map<String, Term> existed = (Map<String, Term>) existedMaps.get(Term.class);
        List<Term> news = (List<Term>) newLists.get(Term.class);
        List<Term> tempNews = new LinkedList<>();
        List<JSONObject> keywords = (List<JSONObject>) jo.get(KEY.Keywords.value());
        for (JSONObject j : keywords) {
            String type = j.getString("type").trim();
            List<String> words = (List<String>) j.get("kwd");
            if (TERM.Author.value().equals(type)) {
                for (String s : words) {
                    Term t = new Term();
                    t.setName(s);
                    authorKeywords.add(t);
                }
            } else if (TERM.IEEE.value().equals(type) || TERM.Ctrl.value().equals(type)) {
                for (String s : words) {
                    Term t;
                    s = StringUtil.stripAccents(s.trim());
                    String name_key = s.toLowerCase();
                    if (existed.containsKey(name_key)) {
                        t = existed.get(name_key);
                    } else if ((t = paperManageService.findTermByName(s)) == null) {
                        t = new Term();
                        t.setName(s);
                        existed.put(name_key, t);
                        tempNews.add(t);
                    }
                    // 避免两个标准中有重复的术语
                    if (!foundTerms.containsKey(name_key)) {
                        foundTerms.put(name_key, null);
                        indexTerms.add(t);
                    }
                }
            }
        }
        paperManageService.saveTermList(tempNews);
        news.addAll(tempNews);
        return terms;
    }

    private static String joinAuthorKeywords(List<Term> authorKeywords) {
        StringBuilder sb = new StringBuilder();
        for (Term t : authorKeywords) {
            sb.append(t.getName()).append(", ");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1).deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    private static List<PaperTerm> analyzePaperTerms(Paper paper, List<Term> terms) {
        List<PaperTerm> paperTermList = new LinkedList<>();
        for (Term t : terms) {
            paperTermList.add(new PaperTerm(paper.getId(), t.getId()));
        }
        return paperTermList;
    }

    private static List<PaperAuthors> analyzePaperAuthors(Paper paper, List<Author> authors) {
        List<PaperAuthors> paperAuthors = new LinkedList<>();
        Iterator<Author> itr = authors.iterator();
        for (int i = 1; itr.hasNext(); i++) {
            paperAuthors.add(new PaperAuthors(itr.next().getId(), paper.getId(), i));
        }
        return paperAuthors;
    }

    private static List<Reference> analyzeReference(Paper paper, JSONObject jo) {
        List<Reference> references = new LinkedList<>();
        JSONArray ja = jo.getJSONArray(KEY.Ref.value());
        for (Object o : ja) {
            JSONObject temp = (JSONObject) o;
            Reference reference = new Reference();
            reference.setArticleId(paper.getArticleId());
            reference.setTitle(temp.getString(REF.Title.value()));
            reference.setReferenceOrder(temp.getInteger(REF.Order.value()));
            reference.setText(temp.getString(REF.Text.value()));
            reference.setRefType(temp.getString(REF.RefType.value()));
            reference.setGoogleScholarLink(temp.getString(REF.GoogleScholarLink.value()));
            references.add(reference);
        }
        return references;
    }

}