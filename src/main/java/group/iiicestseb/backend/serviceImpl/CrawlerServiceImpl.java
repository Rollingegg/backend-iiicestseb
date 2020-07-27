package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.mapper.CrawlerMapper;
import group.iiicestseb.backend.service.CrawlerService;
import group.iiicestseb.backend.utils.PyUtil;
import group.iiicestseb.backend.vo.crawler.CrawlerTaskVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author jh
 * @date 2020/7/13
 */
@Service("Crawler")
public class CrawlerServiceImpl extends ServiceImpl<CrawlerMapper, Crawler> implements CrawlerService {

    @Resource
    CrawlerMapper crawlerMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addCrawler(CrawlerForm form) {
        Crawler crawler = new Crawler(form);
        crawlerMapper.insert(crawler);
        return crawler.getCrawlerId();
    }

    @Override
    public ArrayList<Crawler> getAllCrawler() {
        return crawlerMapper.selectAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean cancelCrawler(Integer crawlerId) {
        Crawler crawler = crawlerMapper.selectById(crawlerId);
        String currentState = crawler.getState();
        PyUtil.killCurrent();
        if (Crawler.STATE.Finished.value.equals(currentState)
                || Crawler.STATE.Canceled.value.equals(currentState)
                || Crawler.STATE.Fail.value.equals(currentState)) {
            return true;
        }
        String state = Crawler.STATE.Canceled.value;
        crawler.setState(state);
        int a = crawlerMapper.updateById(crawler);
        return a >= 1;
    }

    @Override
    public Crawler getNextTask() {
        return crawlerMapper.selectNextTask();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateCrawler(Crawler crawler) {
        int a = crawlerMapper.updateById(crawler);
        return a >= 1;
    }

    @Override
    public void saveLog(Integer crawlerId, String log) {
        crawlerMapper.insertLog(crawlerId, log);
    }

    @Override
    public CrawlerTaskVO getCurrentTask() {
        return PyUtil.getCurrentTask();
    }

    @Override
    public String getLog(Integer crawlerId) {
        return crawlerMapper.selectLogById(crawlerId);
    }


}
