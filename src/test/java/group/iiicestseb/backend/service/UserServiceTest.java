package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Record;
import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.exception.user.UserAlreadyRegisterException;
import group.iiicestseb.backend.exception.user.WrongLoginInfoException;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.mapper.RecordMapper;
import group.iiicestseb.backend.mapper.UserMapper;
import group.iiicestseb.backend.serviceImpl.UserServiceImpl;
import group.iiicestseb.backend.vo.UserVO;
import org.easymock.*;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

@Transactional
public class UserServiceTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @TestSubject
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserMapper userMapper;

    @Mock
    private RecordMapper recordMapper;

    private User user = new User();
    private UserForm userForm = new UserForm();
    List<Record>  recordList = new CopyOnWriteArrayList<Record>();

    @Before
    public void setUp() throws Exception {
        userForm.setUsername("hxd");
        userForm.setPassword("123");
        user.setUsername("hxd");
        user.setPassword("123");
        user.setPrivilegeLevel("用户");
        user.setId(1);
        recordList.add(new Record(1,"nju",1));
    }

    @Test
    public void signInSuccess() {
        EasyMock.expect(userMapper.findByUsername(userForm.getUsername())).andReturn(user);
        EasyMock.expect(recordMapper.findByUserId(user.getId())).andReturn(recordList);
        replayAll();
        UserVO userVO = userService.signIn(userForm);
        assertEquals(user.getId(), userVO.getId());
        assertEquals(user.getUsername(), userVO.getUsername());
        assertEquals(user.getPrivilegeLevel(), userVO.getPrivilegeLevel());
        assertEquals(recordList, userVO.getRecordList());
        verifyAll();
    }

    @Test(expected = WrongLoginInfoException.class)
    public void signInFail() {
        EasyMock.expect(userMapper.findByUsername(userForm.getUsername())).andReturn(null);
        EasyMock.expect(recordMapper.findByUserId(user.getId())).andReturn(null);
        replayAll();
        UserVO userVO = userService.signIn(userForm);
        verifyAll();
    }

    @Test
    public void registerSuccess() {
        EasyMock.expect(userMapper.findByUsername(userForm.getUsername())).andReturn(null);
        EasyMock.expect(userMapper.save(EasyMock.anyObject(User.class))).andReturn(user);
        replayAll();
        try{
            userService.register(userForm);
        }catch (Exception e){
            fail("注册失败");
            throw e;
        }
        verifyAll();
    }

    @Test(expected = UserAlreadyRegisterException.class)
    public void registerFail() {
        EasyMock.expect(userMapper.findByUsername(userForm.getUsername())).andReturn(user);
        replayAll();
        userService.register(userForm);
        verifyAll();
    }

    @Test(expected = UserAlreadyRegisterException.class)
    public void isExistTrue() {
        EasyMock.expect(userMapper.findByUsername(user.getUsername())).andReturn(user);
        replayAll();
        userService.isExist(user.getUsername());
        verifyAll();
    }

    @Test
    public void isExistFalse() {
        EasyMock.expect(userMapper.findByUsername(user.getUsername())).andReturn(null);
        replayAll();
        try{
            userService.isExist(user.getUsername());
        }catch (Exception ex){
            fail();
        }
        verifyAll();
    }
}