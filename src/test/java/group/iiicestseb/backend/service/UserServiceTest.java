//package group.iiicestseb.backend.service;
//
//import group.iiicestseb.backend.form.UserForm;
//import group.iiicestseb.backend.mapper.UserMapper;
//import group.iiicestseb.backend.serviceImpl.UserServiceImpl;
//import org.easymock.*;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.junit.Assert.*;
//
//@Transactional
//public class UserServiceTest extends EasyMockSupport {
//    @Rule
//    public EasyMockRule rule = new EasyMockRule(this);
//
//    @TestSubject
//    private UserService userService = new UserServiceImpl();
//
//    @Mock
//    private UserMapper userMapper;
//    private User user = new User();
//    private UserForm userForm = new UserForm();
//
//    @Before
//    public void setUp() throws Exception {
//        userForm.setUsername("hxd");
//        userForm.setPassword("123");
//        user.setUsername("hxd");
//        user.setPassword("123");
//    }
//
//    @Test
//    public void signIn() {
//        user.setId(2);
//        EasyMock.expect(userMapper.selectByUsername("hxd")).andReturn(user);
//
//        replayAll();
//        assertEquals(userService.signIn(userForm).getId(), (Integer) 2);
//        verifyAll();
//    }
//
//    @Test
//    public void register() {
//        user.setPrivilegeLevel("用户");
//        EasyMock.expect(userMapper.insert(user)).andReturn(1);
//        //EasyMock.expect(userMapper.insert(EasyMock.anyObject()));
//        replayAll();
//        userService.register(userForm);
//        verifyAll();
//    }
//
//    @Test
//    public void judgeUsername() {
//        EasyMock.expect(userMapper.selectByUsername("hxd")).andReturn(user);
//        EasyMock.expect(userMapper.selectByUsername("jh")).andReturn(null);
//        replayAll();
//        assertEquals(userService.judgeUsername("hxd"),true);
//        assertEquals(userService.judgeUsername("jh"),false);
//        verifyAll();
//    }
//}