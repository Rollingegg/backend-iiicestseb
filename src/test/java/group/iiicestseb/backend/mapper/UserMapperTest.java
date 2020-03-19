//package group.iiicestseb.backend.mapper;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//import javax.annotation.Resource;
//
//import static org.junit.Assert.*;
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@Transactional
//public class UserMapperTest {
//
//    @Resource
//    private UserMapper userMapper;
//    private User user = new User();
//
//    @Before
//    public void setUp() throws Exception {
//        user.setUsername("test");
//        user.setUsername("hxd");
//    }
//
//    @Test
//    public void deleteByPrimaryKey() {
//        userMapper.insert(user);
//        assertEquals(userMapper.deleteByPrimaryKey(user.getId()),1);
//        assertEquals(0,userMapper.deleteByPrimaryKey(user.getId()));
//    }
//
//    @Test
//    public void insert() {
//        userMapper.insert(user);
//        assertEquals(userMapper.selectByPrimaryKey(user.getId()),user);
//    }
//
//    @Test
//    public void selectByPrimaryKey() {
//        userMapper.insert(user);
//        assertEquals(userMapper.selectByPrimaryKey(user.getId()),user);
//    }
//
//    @Test
//    public void selectByUsername() {
//        userMapper.insert(user);
//        assertEquals(userMapper.selectByUsername(user.getUsername()),user);
//    }
//
//    @Test
//    public void updateByPrimaryKey() {
//        userMapper.insert(user);
//        user.setPassword("test1");
//        userMapper.updateByPrimaryKey(user);
//        assertEquals(userMapper.selectByPrimaryKey(user.getId()),user);
//    }
//}