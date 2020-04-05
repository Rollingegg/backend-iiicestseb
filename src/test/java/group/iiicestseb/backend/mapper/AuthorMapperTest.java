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
    public void takePlace() {
    }


}