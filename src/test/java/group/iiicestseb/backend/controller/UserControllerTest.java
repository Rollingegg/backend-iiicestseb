package group.iiicestseb.backend.controller;


import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.UserVO;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
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

    @Test
    public void isExist() throws Exception {
        String param = JSON.toJSONString(userForm);
        Mockito.doNothing().when(userService).isExist("test");

        mvc.perform(MockMvcRequestBuilders.get("/user/judge/test",1)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"));
        Mockito.verify(userService).isExist("test");
    }
}