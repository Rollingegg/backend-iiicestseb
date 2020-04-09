package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Record;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RecordMapperTest {

    @Resource
    RecordMapper recordMapper;

    private Record record =  new Record();

    @Before
    public void setUp() throws Exception {

        record.setSearchRecord("sec");
        record.setUserId(1);

    }

    @Test
    public void findByUserId() {
        recordMapper.insert(record);
        assertEquals(record.getSearchRecord(),recordMapper.findByUserId(record.getUserId()).get(0).getSearchRecord());
    }
}