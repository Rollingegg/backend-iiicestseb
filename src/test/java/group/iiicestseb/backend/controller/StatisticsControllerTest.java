package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.serviceImpl.AffiliationServiceImpl;
import group.iiicestseb.backend.serviceImpl.AuthorServiceImpl;
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
import java.util.List;

/**
 * @author jh
 * @date 2020/3/5
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsControllerTest {

    @Resource
    StatisticsMapper statisticsMapper;

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

    @Resource
    AuthorServiceImpl authorService;

    @Resource
    AffiliationServiceImpl affiliationService;

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


    @Test
    public void getAuthorHotTerm() throws  Exception{
        int param =  authorService.findAuthorByName("author1").getId();

        mvc.perform(MockMvcRequestBuilders.get("/statistics/author/hot/term")
                .param("id", Integer.toString(param))
                .param("limit", Integer.toString(10))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[3].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[4].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[5].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[6].count").doesNotExist())
        ;
    }

    @Test
    public void getAffiliationHotTerm() throws Exception{
        int param =  affiliationService.findAffiliationByName("affiliation1").getId();

        mvc.perform(MockMvcRequestBuilders.get("/statistics/affiliation/hot/term")
                .param("id", Integer.toString(param))
                .param("limit", Integer.toString(10))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].count").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[3].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[4].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[5].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[6].count").doesNotExist())
        ;
    }

    @Test
    public void getAuthorPublishCountPerYear() throws Exception{
        int param =  authorService.findAuthorByName("author1").getId();
        mvc.perform(MockMvcRequestBuilders.get("/statistics/author/publish/count/per/year")
                .param("id", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2].count").doesNotExist())
        ;
    }

    @Test
    public void getAffiliationPublishCountPerYear() throws Exception{
        int param =  affiliationService.findAffiliationByName("affiliation1").getId();

        mvc.perform(MockMvcRequestBuilders.get("/statistics/affiliation/publish/count/per/year")
                .param("id", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2].count").doesNotExist())
        ;
    }

    @Test
    public void getTermCountPerYear() throws Exception{
        int param =  statisticsMapper.findTermByName("Control1").getId();

        mvc.perform(MockMvcRequestBuilders.get("/statistics/term/count/per/year")
                .param("id", Integer.toString(param))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[1].count").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2].count").doesNotExist())
        ;
    }
}