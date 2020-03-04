package group.iiicestseb.backend.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class PaperMapperTest {
    @Resource
    private PaperMapper paperMapper;
    @Test
    public void simpleSearchPaperByType() {
        System.out.println(paperMapper.simpleSearchPaperByType("author.name","hxd"));
    }

    @Test
    public void simpleSearchPaperAll() {
        System.out.println(paperMapper.simpleSearchPaperAll("hxd"));
    }

    @Test
    public void advancedSearch() {
    }
}