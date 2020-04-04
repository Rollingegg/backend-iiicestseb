package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.serviceImpl.AuthorServiceImpl;
import group.iiicestseb.backend.utils.JSONUtil;
import org.easymock.EasyMockRule;
import org.easymock.EasyMockSupport;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class AuthorServiceTest extends EasyMockSupport {

    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private AuthorMapper authorMapperMock;

    @TestSubject
    private AuthorService authorServiceStandalone = new AuthorServiceImpl();

    @Resource(name = "Author")
    private AuthorService authorService;

    @Before
    public void setUp() {
        JSONUtil.loadTestData();
    }

    @Test
    public void temp() {
        Author a = authorService.findAuthorByName("author1");
        Assert.assertNotEquals(0, a.getId());
        Assert.assertNotNull(a.getAffiliationId());
        Assert.assertEquals("author1", a.getName());
    }

//    @Test
//    public void getAuthorInfo() {
//
//        Author author = new Author();
//        author.setName("hxd");
//        author.setAffiliationId(1);
//        author.setId(0);
//        EasyMock.expect(authorMapperMock.selectByName("hxd")).andReturn(author);
//        replayAll();
//        AuthorInfoVO result = authorServiceStandalone.getAuthorInfo("hxd");
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
//        EasyMock.expect(authorMapperMock.getAuthorByPaperId(1)).andReturn(authorList);
//        replayAll();
//        CopyOnWriteArrayList<String> result = authorServiceStandalone.getAuthorByPaperId(1);
//        assertEquals(result.get(0),"hxd");
//        assertEquals(result.get(1),"jh");
//        assertEquals(result.get(2),"lwj");
//        verifyAll();
//    }

}