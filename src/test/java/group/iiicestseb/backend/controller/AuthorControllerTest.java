package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
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

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AuthorControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;



    @Resource
    AuthorMapper authorMapper;
    @Resource
    AffiliationMapper affiliationMapper;
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
    }




    @Test
    public void getAuthorInfo() throws Exception{
        Author author = new Author();
        author.setName("hxd");
        Affiliation affiliation =  new Affiliation("nju");
        affiliationMapper.insert(affiliation);
        author.setAffiliationId(affiliation.getId());
        authorMapper.insert(author);

        mvc.perform(MockMvcRequestBuilders.get("/author/info")
                .param("name","hxd")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());
    }
}