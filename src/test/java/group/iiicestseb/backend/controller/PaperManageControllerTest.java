package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperManageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperManageControllerTest {

    private MockMvc mvc;
    private MockHttpSession session;


    @Mock
    PaperManageService paperManageService;

    @InjectMocks
    PaperManageController paperManageController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.standaloneSetup(paperManageController).build();
        session = new MockHttpSession();
    }


    @Test
    public void DeletePaper() throws Exception{

        Mockito.doNothing().when(paperManageService).deletePaperById(1);
        mvc.perform(MockMvcRequestBuilders.delete("/admin/paper/delete")
                .param("id","1")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
        Mockito.verify(paperManageService).deletePaperById(1);
    }


}