package group.iiicestseb.backend.controller;


import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.exception.user.UserAlreadyRegisterException;
import group.iiicestseb.backend.exception.user.WrongLoginInfoException;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.user.UserVO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest{
    @Autowired
    public WebApplicationContext wac;
    private MockMvc mvc;
    private MockMvc mvcStandalone;
    private MockHttpSession session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    @Mock
    private UserService userServiceStub;
    @InjectMocks
    private UserController userControllerUnit = new UserController();

    private UserVO userVO = new UserVO();

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvcStandalone = MockMvcBuilders.standaloneSetup(userControllerUnit).build();
        session = new MockHttpSession();
        //??????session

        MockitoAnnotations.initMocks(this);

        userVO.setPrivilegeLevel("??????");
        userVO.setUsername("testtest");

    }

    /**
     * ????????????
     * @throws Exception ???
     */
    @Test
    public void signInFail() throws Exception {
        UserForm userForm = new UserForm("testtest","testhxd");
        String param = JSON.toJSONString(userForm);
        Mockito.when(userServiceStub.signIn(Mockito.any(UserForm.class))).thenThrow(new WrongLoginInfoException());
        thrown.expect(NestedServletException.class);
        thrown.expectMessage(WrongLoginInfoException.MSG);
        mvcStandalone.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session));
        Mockito.verify(userServiceStub).signIn(userForm);
    }

    /**
     * ????????????
     * @throws Exception ???
     */
    @Test
    public void signInSuccess() throws Exception {
        UserForm userForm = new UserForm("testtest","testhxd");
        String param = JSON.toJSONString(userForm);
        Mockito.when(userServiceStub.signIn(Mockito.any(UserForm.class))).thenReturn(userVO);
        mvcStandalone.perform(MockMvcRequestBuilders.post("/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                //.andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.username").value("testtest"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.privilegeLevel").value("??????"));
        Mockito.verify(userServiceStub).signIn(userForm);
    }

    /**
     * ????????????
     * @throws Exception ???
     */
    @Test
    public void registerSuccess() throws Exception {
        UserForm userForm = new UserForm("testtest","testhxd");
        String param = JSON.toJSONString(userForm);
        Mockito.doNothing().when(userServiceStub).register(userForm);
        mvcStandalone.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
        Mockito.verify(userServiceStub).register(userForm);
    }

    /**
     * ????????????
     * @throws Exception ???
     */
    @Test
    public void registerFail() throws Exception {
        UserForm userForm = new UserForm("testtest","testhxd");
        String param = JSON.toJSONString(userForm);
        Mockito.doThrow(new UserAlreadyRegisterException()).when(userServiceStub).register(userForm);
        thrown.expectMessage(UserAlreadyRegisterException.MSG);
        mvcStandalone.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session));
        Mockito.verify(userServiceStub).register(userForm);
    }

    /**
     * ???????????????????????????
     * @throws Exception ???
     */
    @Test
    public void isExistSuccess() throws Exception {
        Mockito.doNothing().when(userServiceStub).isExist("testset1");
        mvcStandalone.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username","testset1")
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
        Mockito.verify(userServiceStub).isExist("testset1");
    }

    /**
     * isExist?????????????????????????????????
     * @throws Exception ???????????????
     */
    @Test
    public void isExistFail() throws Exception {
        String param = "testset1";
        Mockito.doThrow(new UserAlreadyRegisterException()).when(userServiceStub).isExist("testset1");
        thrown.expect(NestedServletException.class);
        thrown.expectMessage(UserAlreadyRegisterException.MSG);
        mvcStandalone.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username",param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session));
        Mockito.verify(userServiceStub).isExist(param);
    }

    /**
     * ?????????????????????????????????????????????
     * @throws Exception
     */
    @Test
    public void isExistParamException() throws Exception {
        String param = "r";
        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username",param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
        .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserForm.USERNAME_LENGTH_INVALID));


        param = "test test";
        mvc.perform(MockMvcRequestBuilders.get("/user/judge")
                .param("username",param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserForm.USERNAME_CONTAIN_SPACE));
    }
}