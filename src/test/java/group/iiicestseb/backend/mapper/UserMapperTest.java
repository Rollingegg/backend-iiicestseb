package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.assertEquals;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class UserMapperTest {

    @Resource
    private UserMapper userMapper;
    private User user = new User();

    @Before
    public void setUp() throws Exception {
        user.setPassword("testtest");
        user.setUsername("hxdhxd");
    }


    @Test
    public void selectByUsername() {
        userMapper.insert(user);
        assertEquals(user.getUsername(),userMapper.findByUsername(user.getUsername()).getUsername());
        assertEquals(user.getPassword(),userMapper.findByUsername(user.getUsername()).getPassword());
    }
}