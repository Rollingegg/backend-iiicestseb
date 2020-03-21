package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jh
 * @date 2020/3/20
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@Transactional
public class JSONUtilTest {
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private AffiliationMapper affiliationMapper;

    @Test
    public void temp(){
//        JSONUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\Standard.json");
        JSONUtil.analyzeExistedJsonFile("E:\\codes\\backend\\src\\main\\resources\\json\\new_icse15_19(0-50).json");
        System.out.println();
    }

}