//package group.iiicestseb.backend.service;
//
//import group.iiicestseb.backend.mapper.AuthorMapper;
//import group.iiicestseb.backend.serviceImpl.AuthorServiceImpl;
//import group.iiicestseb.backend.vo.author.AuthorInfoVO;
//import org.easymock.*;
//import org.junit.Rule;
//import org.junit.Test;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.concurrent.CopyOnWriteArrayList;
//
//import static org.junit.Assert.*;
//@Transactional
//public class AuthorServiceTest extends EasyMockSupport {
//
//    @Rule
//    public EasyMockRule rule = new EasyMockRule(this);
//
//    @Mock
//    private AuthorMapper authorMapper;
//
//    @TestSubject
//    private AuthorService authorService = new AuthorServiceImpl();
//
//
//    @Test
//    public void getAuthorInfo() {
//
//        Author author = new Author();
//        author.setName("hxd");
//        author.setAffiliationId(1);
//        author.setId(0);
//        EasyMock.expect(authorMapper.selectByName("hxd")).andReturn(author);
//        replayAll();
//        AuthorInfoVO result = authorService.getAuthorInfo("hxd");
//        assertEquals(result.getId(),(Integer) 0);
//        assertEquals(result.getAffiliationId(),(Integer) 1);
//        assertEquals(result.getName(),"hxd");
//        verifyAll();
//    }
//
//    @Test
//    public void getAuthorByPaperId() {
//        CopyOnWriteArrayList<String> authorList = new CopyOnWriteArrayList<>();
//        authorList.add("hxd");
//        authorList.add("jh");
//        authorList.add("lwj");
//        EasyMock.expect(authorMapper.getAuthorByPaperId(1)).andReturn(authorList);
//        replayAll();
//        CopyOnWriteArrayList<String> result = authorService.getAuthorByPaperId(1);
//        assertEquals(result.get(0),"hxd");
//        assertEquals(result.get(1),"jh");
//        assertEquals(result.get(2),"lwj");
//        verifyAll();
//    }
//
//}