package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.utils.CSVUtil;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author jh
 * @date 2020/3/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsControllerTest {

    @Resource(name = "Statistics")
    private StatisticsService statisticsService;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        statisticsService.loadExistedCSV("Standard.csv");
    }

    @Test
    public void analyzeCSVSuccess() throws Exception {
        String param = "Standard.csv";
        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
    }

    @Test
    public void analyzeCSVFileNotExist() throws Exception {
        String param = "aaa.csv";
        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.FILE_NOT_FOUND_ERROR+": " + param));
    }

    @Test
    public void analyzeCSVHeaderError() throws Exception {
        String param = "HeaderError.csv";
        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.HEADER_FORMAT_ERROR));
    }

    @Test
    public void analyzeCSVLineErrorAt5() throws Exception {
        String param = "LineErrorAt5.csv";
        mvc.perform(MockMvcRequestBuilders.post("/statistics/analyzeCSV")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.COL_FORMAT_ERROR + 5));
    }

    @Test
    public void uploadCSVSuccess() throws Exception {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/Standard.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "Standard.csv", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
    }

    @Test
    public void uploadCSVHeaderError() throws Exception {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/HeaderError.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "HeaderError.csv", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.HEADER_FORMAT_ERROR));
    }

    @Test
    public void uploadCSVLineErrorAt5() throws Exception {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/LineErrorAt5.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MockMultipartFile multipartFile = new MockMultipartFile("file", "LineErrorAt5.csv", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/statistics/uploadCSV")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(CSVUtil.COL_FORMAT_ERROR + 5));
    }

    @Test
    public void getHotTermsSuccess() throws Exception {
        int param = 10;
        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[9]").exists());
    }

    @Test
    public void getHotTermsParamTooLargeError() throws Exception {
        int param = 10000;
        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.PARAM_TOO_LARGE));
    }

    @Test
    public void getHotTermsParamNegativeError() throws Exception {
        int param = -2;
        mvc.perform(MockMvcRequestBuilders.get("/statistics/hotTerms")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(StatisticsController.SHOULD_BE_POSITIVE));
    }

    @Test
    public void getMaxPublishAuthorSuccess() throws Exception {
        int param = 5;
        mvc.perform(MockMvcRequestBuilders.get("/statistics/maxPublishAuthor")
                .param("num", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[4]").exists());
    }
}