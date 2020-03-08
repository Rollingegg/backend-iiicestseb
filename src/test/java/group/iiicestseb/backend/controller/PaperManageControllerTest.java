package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperManageControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    private Paper paper = new Paper();

    @Resource
    PaperMapper paperMapper;
    @Resource
    AuthorMapper authorMapper;
    @Resource
    AffiliationMapper affiliationMapper;

    private  Conference c;
    private Publisher p;


    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        c = new Conference("iee");
        p = new Publisher("nju");
        List<Conference> conferenceList = new ArrayList<>();
        List<Publisher> publisherList = new ArrayList<>();
        conferenceList.add(c);
        publisherList.add(p);
        paperMapper.insertConferenceList(conferenceList);
        paperMapper.insertPublisherList(publisherList);
        paper.setConferenceId(c.getId());
        paper.setPublisherId(p.getId());
        paperMapper.insert(paper);
    }


    @Test
    public void DeletePaper() throws Exception{
        String id = paper.getId().toString();
        mvc.perform(MockMvcRequestBuilders.delete("/admin/paper/delete")
                .param("id",id)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
    }


}