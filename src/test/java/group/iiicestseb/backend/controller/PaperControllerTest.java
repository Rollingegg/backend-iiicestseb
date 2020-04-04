package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.serviceImpl.AffiliationServiceImpl;
import group.iiicestseb.backend.serviceImpl.AuthorServiceImpl;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

/**
 * @author jh
 * @date 2020/3/29
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Resource(name = "Author")
    AuthorServiceImpl authorService;

    @Resource
    PaperMapper paperMapper;

    @Resource(name = "Affiliation")
    AffiliationServiceImpl affiliationService;

    ExpectedException thrown = ExpectedException.none();

    private MockMvc mvc;
    private MockMvc mvcStandalone;
    private MockHttpSession session;

    @Mock
    PaperService paperService;

    @InjectMocks
    PaperController paperController;

    private Paper paper;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvcStandalone = MockMvcBuilders.standaloneSetup(paperController).build();
        session = new MockHttpSession();
        paper = paperMapper.selectByArticleId(111111111);
        //JSONUtil.analyzeExistedJsonFile("icse15_19(50).json");
    }

    @Test
    public void getDetailSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/paper/detail")
                .param("paperId", String.valueOf(paper.getId()))
                .param("num", "5")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paper.title").value("Standard1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.conference.name").value("ASE"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.termList[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.referenceList[1]").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.authorList[1]").exists());
    }

    @Test
    public void getRecommendPapersSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/paper/recommend/papers")
                .param("paperId", String.valueOf(paper.getId()))
                .param("num", "5")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].title").value("Standard3"));
    }

    @Test
    public void getRecommendAuthorsSuccess() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/paper/recommend/authors")
                .param("paperId", String.valueOf(paper.getId()))
                .param("num", "5")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].name").exists());
    }

    @Test
    public void getAffiliationsRecentlyPublish() throws Exception{
        Affiliation affiliation = affiliationService.findAffiliationByName("affiliation1");
        mvc.perform(MockMvcRequestBuilders.get("/paper/affiliation/recently/publish")
                .param("id", String.valueOf(affiliation.getId()))
                .param("limit", "10")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].title").value("Standard3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].title").value("Standard1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());

    }

    @Test
    public void getAffiliationsAllPublish() throws Exception{
        Affiliation affiliation = affiliationService.findAffiliationByName("affiliation1");
        mvc.perform(MockMvcRequestBuilders.get("/paper/affiliation/all/publish")
                .param("id", String.valueOf(affiliation.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].title").value("Standard3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].title").value("Standard1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());
    }

    @Test
    public void getAuthorRecentlyPublish() throws  Exception{
        Author author = authorService.findAuthorByName("author1");
        mvc.perform(MockMvcRequestBuilders.get("/paper/author/recently/publish")
                .param("id", String.valueOf(author.getId()))
                .param("limit","10")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].title").value("Standard3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].title").value("Standard1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());
    }

    @Test
    public void getAuthorAllPublish() throws Exception {
        Author author = authorService.findAuthorByName("author1");
        mvc.perform(MockMvcRequestBuilders.get("/paper/author/all/publish")
                .param("id", String.valueOf(author.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].title").value("Standard3"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].title").value("Standard1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").doesNotExist());
    }
}