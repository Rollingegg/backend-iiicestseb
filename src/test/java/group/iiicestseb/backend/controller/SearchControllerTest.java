package group.iiicestseb.backend.controller;


import com.alibaba.fastjson.JSON;
import group.iiicestseb.backend.exception.paper.PaperFormException;
import group.iiicestseb.backend.exception.paper.PaperTypeException;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.paper.SearchResultVO;
import group.iiicestseb.backend.vo.paper.SearchVO;
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

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SearchControllerTest {
    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;
    private MockMvc mvcStandalone;
    private MockHttpSession session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    SearchService searchServiceStub;
    @InjectMocks
    SearchController searchController;


    private List<SearchResultVO> searchResultVOList = new ArrayList<>();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession();
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mvcStandalone = MockMvcBuilders.standaloneSetup(searchController).build();

        SearchResultVO searchResultVO = new SearchResultVO();
        searchResultVO.setId(1);
        searchResultVOList.add(searchResultVO);

    }

    /**
     * ??????????????????
     *
     * @throws Exception ???
     */
    @Test
    public void advancedPaperSuccess() throws Exception {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
        advancedSearchForm.setTermKeyword(null);
        advancedSearchForm.setTitleKeyword("a");
        advancedSearchForm.setAllKeyword("e");
        advancedSearchForm.setAuthorKeyword("Dool");
        advancedSearchForm.setDoiKeyword(null);
        advancedSearchForm.setAffiliationKeyword(null);
        advancedSearchForm.setPaperAbstractKeyword(null);
        advancedSearchForm.setType("advanced");
        //advancedSearchForm.setDoiKeyword("1");
        advancedSearchForm.setLimit(10);
        advancedSearchForm.setPage(0);
        String param = JSON.toJSONString(advancedSearchForm);
        SearchVO searchVO = new SearchVO();
        searchVO.setSearchResultVOCollection(searchResultVOList);

        Mockito.when(searchServiceStub.advancedSearchPaper(Mockito.any(AdvancedSearchForm.class))).thenReturn(searchVO);
        mvcStandalone.perform(MockMvcRequestBuilders.post("/search/advanced")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.searchResultVOCollection[0].id").value(1));
        Mockito.verify(searchServiceStub).advancedSearchPaper(Mockito.any(AdvancedSearchForm.class));
    }


    /**
     * ??????????????????
     */
    @Test
    public void advancedPaperInvalidInfo() throws Exception {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
        advancedSearchForm.setTermKeyword(null);
        advancedSearchForm.setTitleKeyword(null);
        advancedSearchForm.setAllKeyword(null);
        advancedSearchForm.setAuthorKeyword(null);
        advancedSearchForm.setDoiKeyword(null);
        advancedSearchForm.setAffiliationKeyword(null);
        advancedSearchForm.setPaperAbstractKeyword(null);
        advancedSearchForm.setType("advanced");
        //advancedSearchForm.setDoiKeyword("1");
        advancedSearchForm.setLimit(10);
        advancedSearchForm.setPage(0);
        String param = JSON.toJSONString(advancedSearchForm);
        //Mockito.when(searchService.advancedSearchPaper(Mockito.any(AdvancedSearchForm.class))).thenReturn(searchResultVOList);

        mvc.perform(MockMvcRequestBuilders.post("/search/advanced")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PaperFormException.MSG));

        advancedSearchForm.setType(null);
        advancedSearchForm.setAllKeyword("test");
        //advancedSearchForm.setDoiKeyword("1");
        advancedSearchForm.setLimit(10);
        advancedSearchForm.setPage(0);
        param = JSON.toJSONString(advancedSearchForm);
        mvc.perform(MockMvcRequestBuilders.post("/search/advanced")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PaperTypeException.MESSAGE));
        //Mockito.verify(searchService).advancedSearchPaper(advancedSearchForm);

        advancedSearchForm.setLimit(500);
        advancedSearchForm.setType("all");
        advancedSearchForm.setAllKeyword("test");
        //advancedSearchForm.setDoiKeyword("1");
        //advancedSearchForm.setLimit(10);
        advancedSearchForm.setPage(0);
        param = JSON.toJSONString(advancedSearchForm);
        //Mockito.when(searchService.advancedSearchPaper(Mockito.any(AdvancedSearchForm.class))).thenReturn(searchResultVOList);
        mvc.perform(MockMvcRequestBuilders.post("/search/advanced")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(AdvancedSearchForm.LIMIT_ERROR));

        advancedSearchForm.setLimit(10);
        advancedSearchForm.setPage(-1);
        param = JSON.toJSONString(advancedSearchForm);
        //Mockito.when(searchService.advancedSearchPaper(Mockito.any(AdvancedSearchForm.class))).thenReturn(searchResultVOList);
        mvc.perform(MockMvcRequestBuilders.post("/search/advanced")
                .contentType(MediaType.APPLICATION_JSON)
                .content(param)
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("false"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(AdvancedSearchForm.PAGE_ERROR));


    }


}