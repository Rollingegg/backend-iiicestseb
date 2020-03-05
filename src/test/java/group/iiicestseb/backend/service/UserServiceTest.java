package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.mapper.UserMapper;
import group.iiicestseb.backend.serviceImpl.UserServiceImpl;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@Transactional
public class UserServiceTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @TestSubject
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserMapper userMapper;

    @Test
    public void signIn() {
        User user = new User();
        user.setId(2);
        user.setUsername("hxd");
        user.setPassword("123");
        UserForm userForm = new UserForm();
        userForm.setUsername(user.getUsername());
        userForm.setPassword(user.getPassword());
        EasyMock.expect(userMapper.selectByUsername("hxd")).andReturn(user);
        replayAll();
        assertEquals(userService.signIn(userForm).getId(),(Integer) 2);
        verifyAll();
    }

    @Test
    public void register() {
        UserForm userForm = new UserForm();
        userForm.setUsername("jh");
        userForm.setPassword("123");
        userForm.setRecordId(0);
        EasyMock.expect(userMapper.insert(EasyMock.anyObject())).andReturn(1);
        replayAll();
        userService.register(userForm);
        verifyAll();
    }

    @Test
    public void judgeUsername() {
    }
}