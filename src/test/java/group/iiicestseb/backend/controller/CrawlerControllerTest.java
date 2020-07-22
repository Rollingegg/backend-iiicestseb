package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.mapper.CrawlerMapper;
import group.iiicestseb.backend.service.CrawlerService;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.utils.PyUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
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
import java.util.concurrent.TimeUnit;

/**
 * @author jh
 * @date 2020/7/22
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class CrawlerControllerTest {
    @Autowired
    WebApplicationContext wac;

    ExpectedException thrown = ExpectedException.none();

    private MockMvc mvc;
    private MockHttpSession session;

    @Resource
    private CrawlerService crawlerService;
    @Resource
    private CrawlerMapper crawlerMapper;
    @Resource
    private PyUtil pyUtil;

    private Crawler runningCrawler;
    private Crawler waitingCrawler;
    private Crawler finishedCrawler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();

        CrawlerForm form = new CrawlerForm();
        form.setConferenceName(JSONUtil.CONFERENCE.ASE.value);
        form.setStartYear(2010);
        form.setEndYear(2012);
        Integer id1 = crawlerService.addCrawler(form);
        runningCrawler = crawlerMapper.selectById(id1);
        form.setConferenceName(JSONUtil.CONFERENCE.ICSE.value);
        form.setStartYear(2011);
        form.setEndYear(2015);
        Integer id2 = crawlerService.addCrawler(form);
        waitingCrawler = crawlerMapper.selectById(id2);
        form.setConferenceName(JSONUtil.CONFERENCE.ICCV.value);
        form.setStartYear(2015);
        form.setEndYear(2016);
        Integer id3 = crawlerService.addCrawler(form);
        finishedCrawler = crawlerMapper.selectById(id3);
        finishedCrawler.setState(Crawler.STATE.Finished.value);
        finishedCrawler.setStartTime(finishedCrawler.getAddTime().plusHours(2));
        finishedCrawler.setEndTime(finishedCrawler.getAddTime().plusHours(3));
        finishedCrawler.setTotalCount(100);
        finishedCrawler.setSuccessCount(90);
        finishedCrawler.setErrorCount(10);
        crawlerService.updateCrawler(finishedCrawler);
    }

    @After
    public void cleanUp() {
        PyUtil.killCurrent();
    }


    @Test
    public void getAllCrawler() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/crawler/all")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.length()").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[2]").exists());
    }

    @Test
    public void getCurrent() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/crawler/current")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.total").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.current").value(10))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.state").isNotEmpty());
        Assert.assertNotNull(PyUtil.getCurrentTask());
    }

    @Test
    public void cancelCrawler() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/crawler/cancel")
                .param("crawlerId", String.valueOf(waitingCrawler.getCrawlerId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true));
        mvc.perform(MockMvcRequestBuilders.post("/crawler/cancel")
                .param("crawlerId", String.valueOf(runningCrawler.getCrawlerId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true));
        mvc.perform(MockMvcRequestBuilders.post("/crawler/cancel")
                .param("crawlerId", String.valueOf(finishedCrawler.getCrawlerId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(true));
        waitingCrawler = crawlerMapper.selectById(waitingCrawler.getCrawlerId());
        runningCrawler = crawlerMapper.selectById(runningCrawler.getCrawlerId());
        finishedCrawler = crawlerMapper.selectById(finishedCrawler.getCrawlerId());

        Assert.assertEquals(Crawler.STATE.Canceled.value, waitingCrawler.getState());
        Assert.assertEquals(Crawler.STATE.Canceled.value, runningCrawler.getState());
        Assert.assertEquals(Crawler.STATE.Finished.value, finishedCrawler.getState());
    }

    @Test
    public void test() throws Exception {
        Thread thread = new Thread(() -> pyUtil.startNewCrawler(PyUtil.TEST_MAIN_PY));
        thread.start();
        Assert.assertTrue(thread.isAlive());
        TimeUnit.SECONDS.sleep(30);
        Assert.assertTrue(thread.isAlive());
        Assert.assertNotNull(PyUtil.getCurrentTask());
    }


}
