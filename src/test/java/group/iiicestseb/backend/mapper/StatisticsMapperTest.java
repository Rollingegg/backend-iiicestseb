package group.iiicestseb.backend.mapper;

import org.junit.Before;
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
    private StatisticsMapper statisticsMapper;

    @Before
    public void setUp(){
        //todo 统计mapper需要生成测试数据库方法,才能进行测试
    }


    @Test
    public void selectTermsWithHotLimit() {
        //assertEquals(10, statisticsMapper.selectTermsWithHotLimit(10).size());
    }

    @Test
    public void selectMaxPublishAuthorLimit() {
        //assertEquals(10, statisticsMapper.selectTermsWithHotLimit(10).size());
    }
}