package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.UserMapper;
import org.junit.Test;

import javax.annotation.Resource;

import static org.junit.Assert.*;

public class UserServiceImplTest {
    @Resource
    private UserMapper userMapper;
    @Test
    public void test1() {
        userMapper.selectByPrimaryKey(1);
        assertNotNull(userMapper);
    }
}