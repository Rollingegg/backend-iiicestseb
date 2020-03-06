package group.iiicestseb.backend.service;
import group.iiicestseb.backend.utils.DateUtil;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.serviceImpl.SearchServiceImpl;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;


@Transactional
public class SearchServiceTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private PaperMapper paperMapper;

    @TestSubject
    private SearchService searchService = new SearchServiceImpl();


    @Test
    public void simpleSearchPaper() {

//        CopyOnWriteArrayList<Paper> paperList = new CopyOnWriteArrayList<>();
//        Paper paperA = new Paper();
//        paperA.setPaperTitle("hxd");
//        paperA.setId(1);
//        paperA.setCitationCount(10);
//        paperA.setPublicationYear(DateUtil.parseYear("2000"));
//        paperList.add(paperA);
//        EasyMock.expect(paperMapper.simpleSearchPaperAll("hxd")).andReturn(paperList);
//        EasyMock.expect(paperMapper.simpleSearchPaperByType("paper_abstract","hxd")).andReturn(paperList);
//        replayAll();
//        assertEquals(searchService.simpleSearchPaper("all","hxd").get(0).getPaperTitle(),"hxd");
//        assertEquals(searchService.simpleSearchPaper("paper_abstract","hxd").get(0).getPaperTitle(),"hxd");
//        verifyAll();
        System.out.println(searchService.simpleSearchPaper("paper_title","aaaaa"));
        System.out.println(searchService.simpleSearchPaper("paper_title","a"));
    }

    @Test
    public void advancedSearchPaper() {
//        CopyOnWriteArrayList<Paper> paperList = new CopyOnWriteArrayList<Paper>();
//        Paper paperA = new Paper();
//        paperA.setPaperTitle("jh");
//        paperA.setId(1);
//        paperA.setCitationCount(20);
//        paperA.setPublicationYear(DateUtil.parseYear("2000"));
//        paperList.add(paperA);
//        AdvancedSearchForm form = new AdvancedSearchForm();
//        EasyMock.expect(paperMapper.advancedSearch(form)).andReturn(paperList);
//        replayAll();
//        assertEquals(searchService.advancedSearchPaper(form).get(0).getPaperTitle(),"jh");
//        verifyAll();
    }
}