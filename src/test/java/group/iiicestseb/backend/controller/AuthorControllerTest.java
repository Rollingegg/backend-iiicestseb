package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.serviceImpl.AffiliationServiceImpl;
import group.iiicestseb.backend.serviceImpl.AuthorServiceImpl;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AuthorControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Resource(name = "Affiliation")
    AffiliationServiceImpl affiliationService;

    @Resource(name = "Author")
    AuthorServiceImpl authorService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        JSONUtil.loadTestData();
    }


    @Test
    public void getAllAuthorInAffiliation() throws Exception{
        Affiliation affiliation = affiliationService.findAffiliationByName("affiliation1");
        mvc.perform(MockMvcRequestBuilders.get("/author/allin/affiliation")
                .param("id", String.valueOf(affiliation.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());
    }

    @Test
    public void getHotAuthorInAffiliation()throws Exception {
        Affiliation affiliation = affiliationService.findAffiliationByName("affiliation1");
        mvc.perform(MockMvcRequestBuilders.get("/author/hotin/affiliation")
                .param("id", String.valueOf(affiliation.getId()))
                .param("limit", String.valueOf(1))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").doesNotExist());
        mvc.perform(MockMvcRequestBuilders.get("/author/hotin/affiliation")
                .param("id", String.valueOf(affiliation.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());
    }

    @Test
    public void getAuthorPartner() throws Exception{
        Author author = authorService.findAuthorByName("author1");
        mvc.perform(MockMvcRequestBuilders.get("/author/partner")
                .param("id", String.valueOf(author.getId()))
                .param("limit", String.valueOf(10))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[3]").doesNotExist());
    }

    @Test
    public void getAuthorBasicInfo() throws Exception{
        Author author = authorService.findAuthorByName("author1");
        mvc.perform(MockMvcRequestBuilders.get("/author/info")
                .param("id", String.valueOf(author.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paperCount").value(2));
    }
}