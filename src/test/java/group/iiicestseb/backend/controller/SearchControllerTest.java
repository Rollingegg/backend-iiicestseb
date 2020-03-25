package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.entity.Conference;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.exception.paper.PaperTypeException;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.PaperInfoVO;
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
import org.springframework.web.util.NestedServletException;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class SearchControllerTest {

    private MockMvc mvc;
    private MockHttpSession session;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    SearchService searchService;
    @InjectMocks
    SearchController searchController;


    private List<PaperInfoVO> paperInfoVOList = new ArrayList<>();
    private Paper paper = new Paper();
    private Conference conference = new Conference(2, "ieee");
    //private Author author = new Author(3,"hxd","h","xd",2);
    private Term term = new Term(3, "test");
    private AuthorInfoVO authorInfoVO = new AuthorInfoVO(4, "hxd", 5, "nju");
    private PaperInfoVO paperInfoVO;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        session = new MockHttpSession();
        mvc = MockMvcBuilders.standaloneSetup(searchController).build();
        paper.setId(1);
        List<Term> termList = new ArrayList<>();
        termList.add(term);
        AuthorInfoVO authorInfoVO = new AuthorInfoVO();
        authorInfoVO.setId(4);
        List<AuthorInfoVO> authorList = new ArrayList<>();
        authorList.add(authorInfoVO);
        paperInfoVO = new PaperInfoVO(paper, conference, authorList, termList);
        paperInfoVOList.add(paperInfoVO);

    }

    /**
     * 正常搜索情况
     * @throws Exception 无
     */
    @Test
    public void simpleSearchPaperSuccess() throws Exception {
        Mockito.when(searchService.simpleSearchPaper("all", "hxd", 50)).thenReturn(paperInfoVOList);
        mvc.perform(MockMvcRequestBuilders.get("/search/simple")
                .param("type", "all")
                .param("keyword", "hxd")
                .param("limit", "50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].paper.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].conference.id").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].authorInfoList[0].id").value(4))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].termList[0].id").value(3));
        Mockito.verify(searchService).simpleSearchPaper("all", "hxd", 50);
    }

    /**
     * 测试搜索类型错误的情况
     * @throws Exception 搜索类型错误异常
     */
    @Test
    public void simpleSearchPaperTypeError() throws Exception {
        thrown.expect(NestedServletException.class);
        mvc.perform(MockMvcRequestBuilders.get("/search/simple")
                .param("type", "wrongtype")
                .param("keyword", "hxd")
                .param("limit", "50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.status().isInternalServerError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").value(PaperTypeException.MESSAGE));
    }


    @Test
    public void advancedSearchPaper() throws Exception {


        mvc.perform(MockMvcRequestBuilders.get("/search/advanced")
                .param("paper_title", "a")
                .param("paper_abstract", "a")
                .param("doi", "a")
                .param("author_name", "a")
                .param("affiliation_name", "a")
                .param("term", "programs")
                .param("limit", "50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").exists());


        mvc.perform(MockMvcRequestBuilders.get("/search/advanced")
                .param("paper_title", "aaaaaaaaaa")
                .param("paper_abstract", "a")
                .param("doi", "a")
                .param("author_name", "a")
                .param("affiliation_name", "a")
                .param("term", "programs")
                .param("limit", "50")
                .accept(MediaType.APPLICATION_JSON)
                .session(session)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("true"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result").doesNotExist());

    }
}