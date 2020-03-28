package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
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

import java.io.FileInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperManageControllerTest {
    @Autowired
    WebApplicationContext wac;

    private MockMvc mvc;
    private MockHttpSession session;

    @Mock
    PaperManageService paperManageService;

    @InjectMocks
    PaperManageController paperManageController;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }


    @Test
    public void DeletePaperSuccess() throws Exception {

        Mockito.doNothing().when(paperManageService).deletePaperById(1);
        mvc.perform(MockMvcRequestBuilders.delete("/admin/paper/delete")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
        Mockito.verify(paperManageService).deletePaperById(1);
    }

    @Test
    public void analyzeJSONSuccess() throws Exception {
        String param = "One.json";
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/loadJSON")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(0));
    }

    @Test
    public void analyzeJSONFileNotExist() throws Exception {
        String param = "aaa.json";
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/loadJSON")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value(JSONUtil.FILE_NOT_FOUND + param));
    }

    @Test
    public void analyzeJSONExisted() throws Exception {
        String param = "Existed.json";
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/loadJSON")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("第1行：" + JSONUtil.PAPER_EXISTED));
    }

    @Test
    public void analyzeJSONError() throws Exception {
        String param = "Error.json";
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/loadJSON")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("第1行：" + JSONUtil.JSON_PARSE_ERROR));
    }

    @Test
    public void uploadJSONSuccess() throws Exception {
        String path = this.getClass().getResource("/").getPath();
        ClassPathResource file = new ClassPathResource("json/One.json");
        FileInputStream fileInput = new FileInputStream(file.getFile());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "One.json", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/paper/uploadJSON")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(0));
    }

    @Test
    public void uploadJSONExisted() throws Exception {
        ClassPathResource file = new ClassPathResource("json/Existed.json");
        FileInputStream fileInput = new FileInputStream(file.getFile());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "Existed.json", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/paper/uploadJSON")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("第1行：" + JSONUtil.PAPER_EXISTED));
    }

    @Test
    public void uploadJSONError() throws Exception {
        ClassPathResource file = new ClassPathResource("json/Error.json");
        FileInputStream fileInput = new FileInputStream(file.getFile());
        MockMultipartFile multipartFile = new MockMultipartFile("file", "Error.json", "text/plain", fileInput);
        mvc.perform(MockMvcRequestBuilders.multipart("/admin/paper/uploadJSON")
                .file(multipartFile)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .header("Content-type", "multipart/form-data")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").value("第1行：" + JSONUtil.JSON_PARSE_ERROR));
    }

}