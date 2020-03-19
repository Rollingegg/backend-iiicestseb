package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.utils.CSVUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsMapperTest {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Before
    public void setUp(){
        CSVUtil.analyzeExistedCSV("Standard.csv");
    }

    private Record record =  new Record("","");
    @Test
    public void insertUserRecord() {
        assertEquals(1,statisticsMapper.insertUserRecord(record));

    }

    @Test
    public void selectTermsWithHotLimit() {
        assertEquals(10, statisticsMapper.selectTermsWithHotLimit(10).size());
    }

    @Test
    public void selectMaxPublishAuthorLimit() {
        assertEquals(10, statisticsMapper.selectTermsWithHotLimit(10).size());
    }
}