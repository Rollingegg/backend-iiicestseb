package group.iiicestseb.backend.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wph
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AffiliationMapperTest {

    @Resource
    private AffiliationMapper affiliationMapper;


    @Test
    public void selectBasicInfoByName() {
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        //System.out.println(affiliationMapper.selectBasicInfoByName("Chinese Academy of Sciences, Beijing, China"));
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");

    }
}