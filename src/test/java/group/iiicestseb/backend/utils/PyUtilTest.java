package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.mapper.CrawlerMapper;
import group.iiicestseb.backend.service.CrawlerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author jh
 * @date 2020/7/21
 */
@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class PyUtilTest {

    @Resource
    private PyUtil pyUtil;

    @Resource
    private CrawlerService crawlerService;

    @Resource
    private CrawlerMapper crawlerMapper;

    private Crawler runningCrawler;
    private Crawler waitingCrawler;
    private Crawler finishedCrawler;

    @Before
    public void setUp() {
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

    @Test
    public void startNewCrawlerTest() throws Exception {
        //子线程继承主线程的上下文
        RequestContextHolder.setRequestAttributes(RequestContextHolder.currentRequestAttributes(), true);

        pyUtil.startNewCrawler(PyUtil.TEST_MAIN_PY, runningCrawler);
        TimeUnit.SECONDS.sleep(1);
        Assert.assertNotNull(PyUtil.getCurrentTask());
        PyUtil.killCurrent();
        TimeUnit.MILLISECONDS.sleep(50);
        Assert.assertNull(PyUtil.getCurrentTask());
        PyUtil.reset();
    }


}