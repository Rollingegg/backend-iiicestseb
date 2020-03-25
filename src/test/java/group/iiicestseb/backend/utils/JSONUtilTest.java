
package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class JSONUtilTest {
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private AffiliationMapper affiliationMapper;
    @Resource
    private JSONUtil jsonUtil;

    @Before
    public void setup(){
        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\Standard.json");
    }

    @Test
    public void temp() throws Exception{
//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\new_ase13_19(0-605).json");
        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\new_icse15_19(0-815).json");
//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\new_icse10_14(0-1200).json");

//        jsonUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\new_icse15_19(0-50).json");
        System.out.println();
    }

}

