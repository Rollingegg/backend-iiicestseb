package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jh
 * @date 2020/3/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsControllerTest {

    @Mock
    StatisticsService statisticsService;

    @InjectMocks
    StatisticsController statisticsController = new StatisticsController();

    @Resource(name = "Regedit")
    Regedit regedit;

    @Autowired
    WebApplicationContext wac;

    private MockMvc mvc;
    private MockMvc mvcStandAlone;
    private MockHttpSession session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvcStandAlone = MockMvcBuilders.standaloneSetup(statisticsController).build();
        session = new MockHttpSession();
    }


    @Test
    public void getHotTermsParamSuccess() throws Exception {
        Integer param = 50;
        ArrayList<TermWithHotVO> termWithHotVOArrayList = new ArrayList<>();
        TermWithHotVO termWithHotVO_1 = new TermWithHotVO(1, "a", 2);
        TermWithHotVO termWithHotVO_2 = new TermWithHotVO(2, "b", 555);
        termWithHotVOArrayList.add(termWithHotVO_1);
        termWithHotVOArrayList.add(termWithHotVO_2);

        Mockito.when(statisticsService.findHotTerms(param)).thenReturn(termWithHotVOArrayList);
        mvcStandAlone.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())

                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
        Mockito.verify(statisticsService).findHotTerms(param);
    }

    @Test
    public void getMaxPublishAuthorSuccess() throws Exception {
        int param = 5;
        List<AuthorHotVO> authorHotVOList = new ArrayList<>();
        AuthorHotVO authorHotVO_1 = new AuthorHotVO(1, "jh", 1, "nju", 100);
        AuthorHotVO authorHotVO_2 = new AuthorHotVO(2, "hxd", 1, "zju", 200);
        authorHotVOList.add(authorHotVO_1);
        authorHotVOList.add(authorHotVO_2);
        Mockito.when(statisticsService.calculateMaxPublishAuthor(param)).thenReturn(authorHotVOList);
        mvcStandAlone.perform(MockMvcRequestBuilders.get("/statistics/maxPublishAuthor")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").value(2));
        Mockito.verify(statisticsService).calculateMaxPublishAuthor(param);
    }

    @Test
    public void getRelativeTermsOfTermSuccess() throws Exception {
        Term term2 = regedit.findTermByName("Control2");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/relativeTermsOfTerm")
                .param("termId", Integer.toString(term2.getId()))
                .param("max", Integer.toString(5))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(5));
    }

    @Test
    public void getPapersByTermIdInScoreOrderSuccess() throws Exception {
        Term term1 = regedit.findTermByName("Control5");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/relativePapersOfTerm")
                .param("termId", Integer.toString(term1.getId()))
                .param("max", Integer.toString(5))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(1));
        Term term2 = regedit.findTermByName("Control2");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/relativePapersOfTerm")
                .param("termId", Integer.toString(term2.getId()))
                .param("max", Integer.toString(5))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2));
    }

    @Test
    public void getActiveAuthorsOfTermSuccess() throws Exception {
        Term term1 = regedit.findTermByName("Control5");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/activeAuthorsOfTerm")
                .param("termId", Integer.toString(term1.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2));
        Term term2 = regedit.findTermByName("Control2");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/activeAuthorsOfTerm")
                .param("termId", Integer.toString(term2.getId()))
                .param("max", Integer.toString(5))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(3));
    }

    @Test
    public void getActiveAffiliationOfTermSuccess() throws Exception {
        Term term1 = regedit.findTermByName("Control5");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/activeAffiliationOfTerm")
                .param("termId", Integer.toString(term1.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(1));
        Term term2 = regedit.findTermByName("Control2");
        mvc.perform(MockMvcRequestBuilders.get("/statistics/activeAffiliationOfTerm")
                .param("termId", Integer.toString(term2.getId()))
                .param("max", Integer.toString(5))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(3));

    }

//    /**
//     * 获取热度术语产生严重错误
//     * @throws Exception 未知错误
//     */
//    @Test
//    public void getHotTermsParamError() throws Exception {
//        Integer param = 50;
//        ArrayList<TermWithHotVO> termWithHotVOArrayList = new ArrayList<>();
//        TermWithHotVO termWithHotVO_1 = new TermWithHotVO(1,"a",2);
//        TermWithHotVO termWithHotVO_2 = new TermWithHotVO(2,"b",555);
//        termWithHotVOArrayList.add(termWithHotVO_1);
//        termWithHotVOArrayList.add(termWithHotVO_2);
//
//        Mockito.when(statisticsService.calculateHotTerms(param)).thenThrow(new RuntimeException());
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.GET_HOT_TERMS_ERROR));
//        Mockito.verify(statisticsService).calculateHotTerms(param);
//    }
//
//
//    /**
//     * 获取热度作者出现未知错误
//     * @throws Exception 未知错误
//     */
//    @Test
//    public void getMaxPublishAuthorError() throws Exception {
//        int param = 5;
//        List<AuthorHotVO> authorHotVOList = new ArrayList<>();
//        AuthorHotVO authorHotVO_1 = new AuthorHotVO(1,"jh","nju",100);
//        AuthorHotVO authorHotVO_2 = new AuthorHotVO(2,"hxd","zju",200);
//        authorHotVOList.add(authorHotVO_1);
//        authorHotVOList.add(authorHotVO_2);
//        Mockito.when(statisticsService.calculateMaxPublishAuthor(param)).thenThrow(new RuntimeException());
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/maxPublishAuthor")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.GET_MAX_PUBLISH_AUTHOR_ERROR));
//        Mockito.verify(statisticsService).calculateMaxPublishAuthor(param);
//    }


//    @Test
//    public void analyzeCSVSuccess() throws Exception {
//        String param = "Standard.csv";
//        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
//                .param("filename", param)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
//    }
//
//    @Test
//    public void analyzeCSVFileNotExist() throws Exception {
//        String param = "aaa.csv";
//        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
//                .param("filename", param)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.FILE_NOT_FOUND_ERROR+": " + param));
//    }
//
//    @Test
//    public void analyzeCSVHeaderError() throws Exception {
//        String param = "HeaderError.csv";
//        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
//                .param("filename", param)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.HEADER_FORMAT_ERROR));
//    }
//
//    @Test
//    public void analyzeCSVLineErrorAt5() throws Exception {
//        String param = "LineErrorAt5.csv";
//        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
//                .param("filename", param)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
//    }
//
//    @Test
//    public void uploadCSVSuccess() throws Exception {
//        String path = this.getClass().getResource("/").getPath();
//        String filename = path + "csv/Standard.csv";
//        File file = new File(filename);
//        FileInputStream fileInput = new FileInputStream(file);
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "Standard.csv", "text/plain", fileInput);
//        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
//                .file(multipartFile)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .header("Content-type", "multipart/form-data")
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
//    }
//
//    @Test
//    public void uploadCSVHeaderError() throws Exception {
//        String path = this.getClass().getResource("/").getPath();
//        String filename = path + "csv/HeaderError.csv";
//        File file = new File(filename);
//        FileInputStream fileInput = new FileInputStream(file);
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "HeaderError.csv", "text/plain", fileInput);
//        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
//                .file(multipartFile)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .header("Content-type", "multipart/form-data")
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.HEADER_FORMAT_ERROR));
//    }
//
//    @Test
//    public void uploadCSVLineErrorAt5() throws Exception {
//        String path = this.getClass().getResource("/").getPath();
//        String filename = path + "csv/LineErrorAt5.csv";
//        File file = new File(filename);
//        FileInputStream fileInput = new FileInputStream(file);
//        MockMultipartFile multipartFile = new MockMultipartFile("file", "LineErrorAt5.csv", "text/plain", fileInput);
//        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
//                .file(multipartFile)
//                .accept(MediaType.APPLICATION_JSON)
//                .contentType(MediaType.MULTIPART_FORM_DATA)
//                .header("Content-type", "multipart/form-data")
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors[0].msg").value(CSVUtil.COL_FORMAT_ERROR + 5))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errors[0].row").value( 5));
//    }

//    @Test
//    public void getHotTermsSuccess() throws Exception {
//        int param = 10;
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result[9]").exists());
//    }
//
//    @Test
//    public void getHotTermsParamTooLargeError() throws Exception {
//        int param = 10000;
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.PARAM_TOO_LARGE));
//    }
//
//    @Test
//    public void getHotTermsParamNegativeError() throws Exception {
//        int param = -2;
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.SHOULD_BE_POSITIVE));
//    }
//
//    @Test
//    public void getMaxPublishAuthorSuccess() throws Exception {
//        int param = 5;
//        mvc.perform(MockMvcRequestBuilders.get("/statistics/maxPublishAuthor")
//                .param("num", Integer.toString(param))
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session)
//        ).andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result[4]").exists());
//    }
}