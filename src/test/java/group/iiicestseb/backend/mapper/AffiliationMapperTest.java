package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.fail;

/**
 * @author wph
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AffiliationMapperTest {

    @Resource
    private AffiliationMapper affiliationMapper;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
    }

    @Test
    public void selectAffiliationInfoByName() {
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println(affiliationMapper.selectAffiliationInfoByName("Chinese Academy of Sciences, Beijing, China"));
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");

    }

    @Test
    public void selectAffiliationInfoByIdBatch() {
        Affiliation a1 = affiliationMapper.selectByName("affiliation1");
        Affiliation a2 = affiliationMapper.selectByName("affiliation2");
        Collection<Integer> ids = new LinkedList<>() {{
            add(a1.getId());
            add(a2.getId());
        }};
        Collection<AffiliationInfoVO> infos = affiliationMapper.selectAffiliationInfoByIdBatch(ids);
        for (AffiliationInfoVO i : infos) {
            if (a1.getId().equals(i.getId())){
                Assert.assertEquals(a1.getName(), i.getName());
                Assert.assertEquals(2, (int) i.getAuthorNum());
                Assert.assertEquals(2, (int) i.getPaperNum());
            } else if (a2.getId().equals(i.getId())){
                Assert.assertEquals(a2.getName(), i.getName());
                Assert.assertEquals(1, (int) i.getAuthorNum());
                Assert.assertEquals(2, (int) i.getPaperNum());
            } else {
                fail();
            }
        }
//            AffiliationInfoVO wph = affiliationMapper.selectBasicInfoByName(jh.getName());
//            System.out.println("wph:" + wph.getAuthorNum() + "   " + wph.getPaperNum());
//            System.out.println("my:" + jh.getAuthorNum() + "    " + jh.getPaperNum());
//        Collection<AffiliationInfoVO> affiliationInfoVOS = affiliationMapper.selectBasicInfoByIdBatch(new LinkedList<>() {{
//            add(177);
//            add(178);
//        }});
//        System.out.println(affiliationInfoVOS.toString());
//        for (AffiliationInfoVO jh : affiliationInfoVOS) {
//            AffiliationInfoVO wph = affiliationMapper.selectBasicInfoByName(jh.getName());
//            System.out.println("wph:" + wph.getAuthorNum() + "   " + wph.getPaperNum());
//            System.out.println("my:" + jh.getAuthorNum() + "    " + jh.getPaperNum());
//        }

    }
}