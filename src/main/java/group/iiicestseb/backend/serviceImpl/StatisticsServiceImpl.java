package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.utils.CSVUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Statistics")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private AffiliationMapper affiliationMapper;

    @Resource
    private AuthorMapper authorMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    StatisticsMapper statisticsMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void loadCsv(String filename) {
        CSVUtil.analyzeCsv(filename);
    }

    @Override
    public int createUserRecord() {
        return statisticsMapper.insertUserRecord();
    }

}