
package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class JSONUtilTest {
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private AffiliationMapper affiliationMapper;




    @Test
    public void temp() throws Exception{
//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\ase13_19(605).json");
//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\icse15_19(815).json");
//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\icse10_14(1200).json");

//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\icse15_19(50).json");
//        JSONUtil.loadTestData();
        System.out.println();
    }

}

