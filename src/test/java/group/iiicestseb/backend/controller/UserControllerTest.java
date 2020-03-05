package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.entity.User;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.vo.UserVO;
import org.easymock.EasyMockRule;
import org.easymock.Mock;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.ModelResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;



@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class UserControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    private UserForm userForm = new UserForm();
    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        //加入session

        userForm.setPassword("gg");
        userForm.setUsername("hxd");
        session.setAttribute("UserForm", userForm);
    }




    @Test
    public void signInAndRigisterAndJudge() throws Exception {
        //没用账号用户登录
        String param = JSON.toJSONString(userForm);  //其中u是VO对象



        mvc.perform(MockMvcRequestBuilders.post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON).content(param)
                        .accept(MediaType.APPLICATION_JSON)
                        .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.WRONG_LOGIN_INFO));
        //查看此用户注册没
        mvc.perform(MockMvcRequestBuilders.get("/user/judge/{username}","hxd")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
        //注册
        mvc.perform(MockMvcRequestBuilders.post("/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk()) //验证响应contentType
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());
        //查看此用户已注册
        mvc.perform(MockMvcRequestBuilders.get("/user/judge/{username}","hxd")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(UserController.USER_EXIST));
    }
}