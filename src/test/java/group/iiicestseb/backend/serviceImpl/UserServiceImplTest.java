package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void test1() {
        User user = userMapper.selectByPrimaryKey(1);
        assertEquals(user.getUsername(),"a");
    }
}