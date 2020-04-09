package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.vo.statistics.GeneralCountPerYearVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class StatisticsMapperTest {

    @Resource
    private StatisticsMapper statisticsMapper;

    @Resource
    private AffiliationMapper affiliationMapper;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
    }

    @Test
    public void selectTermsWithHotLimit() {
        Affiliation affiliation = affiliationMapper.selectByName("affiliation1");
        Collection<GeneralCountPerYearVO> vos = statisticsMapper.getAffiliationPublishCountPerYear(affiliation.getId());
        Assert.assertNotEquals(0, vos.size());
    }

    @Test
    public void selectMaxPublishAuthorLimit() {
        Assert.assertEquals(5, statisticsMapper.selectTermsWithHotLimit(5).size());
    }


}