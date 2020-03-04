package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Record;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsMapperTest {

    @Resource
    StatisticsMapper statisticsMapper;
    @Test
    public void insertUserRecord() {

        statisticsMapper.insertUserRecord(new Record("",""));
    }
}