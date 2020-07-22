package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.mapper.CrawlerMapper;
import group.iiicestseb.backend.service.CrawlerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

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
    public void test() {
        System.out.println(System.getProperty("user.dir"));
        pyUtil.startNewCrawler(PyUtil.TEST_MAIN_PY);
    }


}