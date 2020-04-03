package group.iiicestseb.backend.mapper;

import org.junit.Before;
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
public class AuthorMapperTest {
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private AuthorStatisticsMapper authorStatisticsMapper;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void selectHotAuthorByAffiliationName() {
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        //System.out.println(authorMapper.selectHotAuthorByAffiliationName("Chinese Academy of Sciences, Beijing, China",10));
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
    }

    @Test
    public void selectAllAuthorByAffiliationName() {
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        //System.out.println(authorMapper.selectAllAuthorByAffiliationName("Chinese Academy of Sciences, Beijing, China"));
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
        System.out.println("------------------------------testtestst");
    }
}