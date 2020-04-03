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

    }




    @Test
    public void selectTermsWithHotLimit() {
        System.out.println("ttttttttttttttttttttttttttttttttttttttt");
        System.out.println(statisticsMapper.getAffiliationPublishCountPerYear(1));

    }

    @Test
    public void selectMaxPublishAuthorLimit() {
        //assertEquals(10, statisticsMapper.selectTermsWithHotLimit(10).size());
    }


}