package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.serviceImpl.AffiliationServiceImpl;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class AffiliationControllerTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockHttpSession session;

    @Resource(name = "Affiliation")
    AffiliationServiceImpl affiliationService;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        session = new MockHttpSession();
        JSONUtil.loadTestData();
    }


    @Test
    public void getAffiliationBasicInfo() throws Exception {
        Affiliation affiliation = affiliationService.findAffiliationByName("affiliation1");
        mvc.perform(MockMvcRequestBuilders.get("/affiliation/info")
                .param("id", String.valueOf(affiliation.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .session(session))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.paperNum").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.authorNum").value(2));

    }
}