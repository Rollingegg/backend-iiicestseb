package group.iiicestseb.backend.service;

import group.iiicestseb.backend.exception.paper.NoPaperFoundException;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.serviceImpl.SearchServiceImpl;
import group.iiicestseb.backend.vo.SearchResultVO;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.assertEquals;


@Transactional
public class SearchServiceTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private PaperMapper paperMapper;

    @TestSubject
    private SearchService searchService = new SearchServiceImpl();


    @Test
    public void advancedSearchPaperSuccess() {
        List<SearchResultVO> searchResultVOList = new CopyOnWriteArrayList<SearchResultVO>();
        SearchResultVO searchResultVO = new SearchResultVO();
        searchResultVO.setId(1);
        searchResultVO.setCitationCountPaper(1);
        searchResultVO.setPdfUrl("www");
        searchResultVO.setTitle("do");
        searchResultVO.setPaperAbstract("nothing");
        searchResultVO.setAuthorList(null);
        searchResultVO.setTermsList(null);
        searchResultVOList.add(searchResultVO);
        AdvancedSearchForm form = new AdvancedSearchForm();
        form.setType("all");
        form.setAllKeyword("1");
        form.setPage(0);
        EasyMock.expect(paperMapper.advancedSearch(EasyMock.anyObject())).andReturn(searchResultVOList);
        replayAll();
        //searchService.advancedSearchPaper(form);
        assertEquals(searchResultVO,searchService.advancedSearchPaper(form).get(0));
        verifyAll();
    }

    @Test(expected = NoPaperFoundException.class)
    public void advancedSearchPaperFail() {
        List<SearchResultVO> searchResultVOList = new CopyOnWriteArrayList<SearchResultVO>();
//        SearchResultVO searchResultVO = new SearchResultVO();
//        searchResultVO.setId(1);
//        searchResultVOList.add(searchResultVO);
        AdvancedSearchForm form = new AdvancedSearchForm();
        form.setType("all");
        form.setAllKeyword("1");
        form.setPage(0);
        EasyMock.expect(paperMapper.advancedSearch(EasyMock.anyObject())).andThrow(new NoPaperFoundException());
        replayAll();
        searchService.advancedSearchPaper(form);
        //assertEquals(1,(int)searchService.advancedSearchPaper(form).get(0).getId());
        verifyAll();
    }
}