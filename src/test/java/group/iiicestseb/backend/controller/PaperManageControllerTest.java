package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
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

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperManageControllerTest {
    @Autowired
    WebApplicationContext wac;

    ExpectedException thrown = ExpectedException.none();

    private MockMvc mvc;
    private MockMvc mvcStandalone;
    private MockHttpSession session;

    @Mock
    PaperManageService paperManageServiceMock;

    @InjectMocks
    PaperManageController paperManageController;

    @Resource(name = "Regedit")
    PaperManageService paperManageService;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvcStandalone = MockMvcBuilders.standaloneSetup(paperManageController).build();
        session = new MockHttpSession();
    }

    @Test
    public void DeletePaperSuccess() throws Exception {
        Mockito.doNothing().when(paperManageServiceMock).deletePaperById(1);
        mvcStandalone.perform(MockMvcRequestBuilders.delete("/admin/paper/delete")
                .param("id", "1")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
        Mockito.verify(paperManageServiceMock).deletePaperById(1);
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.successCount").value(1));
    }

    @Test
    public void analyzeJSONFileNotExist() throws Exception {
        String param = "aaa.json";
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/loadJSON")
                .param("filename", param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(JSONUtil.FILE_NOT_FOUND + param));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorLogs[0]").value("第1行：" + JSONUtil.PAPER_EXISTED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.existedCount").value(1));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorLogs[0]").value("第1行：" + JSONUtil.JSON_PARSE_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorCount").value(1));
    }

    @Test
    public void uploadJSONSuccess() throws Exception {
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.successCount").value(1));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorLogs[0]").value("第1行：" + JSONUtil.PAPER_EXISTED))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.existedCount").value(1));
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
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorLogs[0]").value("第1行：" + JSONUtil.JSON_PARSE_ERROR))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.totalCount").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.errorCount").value(1));
    }

    @Test
    public void reComputeScoreSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/computeNewPaperScore")
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
    }

    @Test
    public void findPaperStatisticsSuccess() throws Exception {
        Paper p = paperManageService.findPaperByArticleId(111111111);
        mvc.perform(MockMvcRequestBuilders.get("/admin/paper/paperScore")
                .param("paperId", String.valueOf(p.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paperId").value(p.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score").value(-0.06067));
    }

    @Test
    public void updatePaperStatisticsSuccess() throws Exception {
        Paper p = paperManageService.findPaperByArticleId(111111111);
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/paperScore")
                .param("paperId", String.valueOf(p.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paperId").value(p.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.score").value(-0.060666666));
    }

    @Test
    public void updatePaperStatisticsBatchSuccess() throws Exception {
        Paper p1 = paperManageService.findPaperByArticleId(111111111);
        Paper p2 = paperManageService.findPaperByArticleId(333333333);
        List<Integer> list = new LinkedList<>() {{
            add(p1.getId());
            add(p2.getId());
        }};
        mvc.perform(MockMvcRequestBuilders.post("/admin/paper/paperScoreBatch")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSON.toJSONString(list))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].paperId").value(p1.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].score").value(-0.060666666))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].paperId").value(p2.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].score").value(0.009166667));
    }




}