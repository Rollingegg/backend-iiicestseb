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
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

@Transactional
public class UserServiceTest extends EasyMockSupport {
    @Mock
    private UserMapper userMapper;

    @Mock
    private RecordMapper recordMapper;

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    private User user = new User();
    private UserForm userForm = new UserForm();
    private List<Record> recordList = new CopyOnWriteArrayList<Record>();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
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
        Mockito.when(userMapper.findByUsername(userForm.getUsername())).thenReturn(user);
        Mockito.when(recordMapper.findByUserId(user.getId())).thenReturn(recordList);
        UserVO userVO = userService.signIn(userForm);
        assertEquals(user.getId(), userVO.getId());
        assertEquals(user.getUsername(), userVO.getUsername());
        assertEquals(user.getPrivilegeLevel(), userVO.getPrivilegeLevel());
        assertEquals(recordList, userVO.getRecordList());
        Mockito.verify(userMapper).findByUsername(userForm.getUsername());
    }

    @Test(expected = WrongLoginInfoException.class)
    public void signInFail() {
        Mockito.when(userMapper.findByUsername(userForm.getUsername())).thenReturn(null);
        Mockito.when(recordMapper.findByUserId(user.getId())).thenReturn(null);
        UserVO userVO = userService.signIn(userForm);
        assertNull(userVO);
        Mockito.verify(userMapper).findByUsername(userForm.getUsername());
        Mockito.verify(recordMapper).findByUserId(user.getId());
    }

    @Test
    public void registerSuccess() {
        Mockito.when(userMapper.findByUsername(userForm.getUsername())).thenReturn(null);
        Mockito.when(userMapper.insert(Mockito.any(User.class))).thenReturn(1);
        try{
            userService.register(userForm);
        }catch (Exception e){
            fail("注册失败");
            //throw e;
        }
        Mockito.verify(userMapper).findByUsername(userForm.getUsername());
        Mockito.verify(userMapper).insert(Mockito.any(User.class));
    }

    @Test(expected = UserAlreadyRegisterException.class)
    public void registerFail() {
        Mockito.when( userMapper.findByUsername(userForm.getUsername())).thenReturn(user);
        userService.register(userForm);
        Mockito.verify(userMapper).findByUsername(userForm.getUsername());
    }

    @Test(expected = UserAlreadyRegisterException.class)
    public void isExistTrue() {
        Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(user);
        userService.isExist(user.getUsername());
        Mockito.verify(userMapper).findByUsername(user.getUsername());
    }

    @Test
    public void isExistFalse() {
        Mockito.when(userMapper.findByUsername(user.getUsername())).thenReturn(null);
        try{
            userService.isExist(user.getUsername());
        }catch (Exception ex){
            fail();
        }
        Mockito.verify(userMapper).findByUsername(user.getUsername());
    }
}