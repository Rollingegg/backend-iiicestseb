package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.service.StatisticsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.annotation.Resources;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Statistics")
public class StatisticsServiceImpl implements StatisticsService {
    @Resource
    StatisticsMapper statisticsMapper;


    @Override
    public int createUserRecord() {
        return statisticsMapper.insertUserRecord();
    }
}
