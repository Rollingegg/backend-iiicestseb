package group.iiicestseb.backend.controller;


import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.UserVO;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest extends EasyMockSupport {

    private MockMvc mvc;
    private MockHttpSession session;


    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private UserService userService;

    private UserForm userForm = new UserForm();
    private UserVO userVO = new UserVO();

    @InjectMocks
    private UserController userController = new UserController();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.standaloneSetup(userController).build();
        session = new MockHttpSession();
        //加入session

        userForm.setPassword("testtest");
        userForm.setUsername("hxdhxdhxf");

        MockitoAnnotations.initMocks(this);

        userVO.setPrivilegeLevel("用户");
        userVO.setUsername("hxdhxd");

    }

    /**
     * 正常登录
     * @throws Exception 无
     */
    @Test
    public void signIn() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.when(userService.signIn(userForm)).thenReturn(userVO);

        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
        .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.username").value("hxdhxd"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.privilegeLevel").value("用户"));
        Mockito.verify(userService).signIn(userForm);
    }

    /**
     * 发生严重的未知登录错误
     * @throws Exception 登录未知错误
     */
    @Test
    public void signInError() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.when(userService.signIn(userForm)).thenThrow(new RuntimeException());

        //thrown.expect(NestedServletException.class);
        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.SIGN_IN_ERROR));
        Mockito.verify(userService).signIn(userForm);
    }


    /**
     * 发生严重的未知注册错误
     * @throws Exception 注册未知错误
     */
    @Test
    public void registerError() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.doThrow(new RuntimeException()).when(userService).register(userForm);
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.REGISTER_IN_ERROR));
        Mockito.verify(userService).register(userForm);
    }

    /**
     * 注册成功
     * @throws Exception 无
     */
    @Test
    public void register() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.doNothing().when(userService).register(userForm);

        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
        Mockito.verify(userService).register(userForm);
    }

    /**
     * 注册事判断用户不存在
     * @throws Exception 无
     */
    @Test
    public void isExist() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.doNothing().when(userService).isExist("testset");

        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username","testset")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
        Mockito.verify(userService).isExist("testset");
    }


    /**
     * 注册事判断用户发生严重的未知错误
     * @throws Exception 无
     */
    @Test
    public void isExistError() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.doThrow(new RuntimeException()).when(userService).isExist("testset");
        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username","testset")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.IS_EXIST_ERROR));
        Mockito.verify(userService).isExist("testset");
    }


    /**
     * 验证userform表单是否正确
     * @throws Exception
     */
    @Test
    public void validUserForm() throws Exception {
        thrown.expectMessage(UserForm.USERNAME_EMPTY);
        userForm.setUsername(null);
        thrown.expectMessage(UserForm.PASSWORD_EMPTY);
        userForm.setPassword(null);
        thrown.expectMessage(UserForm.USERNAME_LENGTH_INVALID);
        userForm.setUsername("rwer");
        thrown.expectMessage(UserForm.PASSWORD_LENGTH_INVALID);
        userForm.setPassword("1233");
        thrown.expectMessage(UserForm.USERNAME_CONTAIN_SPACE);
        userForm.setUsername("hxd jhaaa");
        thrown.expectMessage(UserForm.PASSWORD_CONTAIN_SPACE);
        userForm.setPassword("123 1233");
    }

//    /**
//     * isExist传入的username格式不对
//     * @throws Exception
//     */
//    @Test
//    public void isExistInvalidUsername() throws Exception {
//        String param = "test";
//        //名字长度不对
//        thrown.expect(Exception.class);
//        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
//                .param("username",param)
//                .accept(MediaType.APPLICATION_JSON)
//                .session(session))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.USERNAME_INVALID_LENGTH));
//        Mockito.verify(userService).isExist(param);
//
//        //名字有空格
//        param = JSON.toJSONString("test test");
//        thrown.expectMessage(UserController.USERNAME_INVALID_LENGTH);
//        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
//                .contentType(MediaType.APPLICATION_JSON)
//                .param("username",param)
//                .session(session))
//                .andExpect(MockMvcResultMatchers.status().isOk())
//                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.USNAME_EMPTY));
//        Mockito.verify(userService).isExist("test");
//    }
}