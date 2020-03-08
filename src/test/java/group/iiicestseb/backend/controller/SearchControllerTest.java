package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.service.StatisticsService;
import org.apache.ibatis.annotations.ResultMap;
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
public class SearchControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;
    @Resource
    StatisticsService statisticsService;
    @Resource
    PaperMapper paperMapper;
    @Resource
    AuthorMapper authorMapper;
    @Resource
    AffiliationMapper affiliationMapper;
    private Paper paper1;
    private Paper paper2;
    private  Conference c;
    private Publisher p;
    private Affiliation a;

    private void createPaper(Paper paper,String paper_title,String doi,String abstarct,String authorname,String affiliationname){
//        paper = new Paper();
//        paper.setPaperTitle(paper_title);
//        paper.setPaperAbstract(doi);
//        paper.setDoi(doi);
//        Author author = new Author();
//        author.setName(authorname);
//        a = new Affiliation();
//        a.setName(affiliationname);
//        affiliationMapper.insert(a);
//        author.setAffiliationId(a.getId());
//        authorMapper.insert(author);
//        paper.setPublisherId(p.getId());
//        paper.setConferenceId(c.getId());
//        paperMapper.insert(paper);
//        Publish publish = new Publish(paper,author);
//        List<Publish> publishes = new ArrayList<>();
//        publishes.add(publish);
//        paperMapper.insertPublishList(publishes);



    }


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
        statisticsService.loadExistedCSV("Standard.csv");




        createPaper(paper1,"a","100","test1","hxd","nju");
        createPaper(paper2,"b","1010","test2","jh","nju");
    }
    @Test
    public void simpleSearchPaper() throws Exception{
        mvc.perform(MockMvcRequestBuilders.get("/search/simple")
                .param("type", "author_name")
                .param("keyword", "an")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists())
        ;

        mvc.perform(MockMvcRequestBuilders.get("/search/simple")
                .param("type", "author_name")
                .param("keyword", "aaaaaaaa")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
    }

    @Test
    public void advancedSearchPaper() throws Exception{



        mvc.perform(MockMvcRequestBuilders.get("/search/advanced")
                .param("paper_title","a")
                .param("paper_abstract","a")
                .param("doi","a")
                .param("author_name","a")
                .param("affiliation_name","a")
                .param("term","programs")
                .param("limit","50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());


        mvc.perform(MockMvcRequestBuilders.get("/search/advanced")
                .param("paper_title","aaaaaaaaaa")
                .param("paper_abstract","a")
                .param("doi","a")
                .param("author_name","a")
                .param("affiliation_name","a")
                .param("term","programs")
                .param("limit","50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());

    }
}