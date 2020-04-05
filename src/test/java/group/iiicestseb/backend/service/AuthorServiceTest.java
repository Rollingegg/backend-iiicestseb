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
    public void findAuthorByNameSuccess() {
        Author a = authorService.findAuthorByName("author1");
        Assert.assertNotEquals(0, a.getId());
        Assert.assertNotNull(a.getAffiliationId());
        Assert.assertEquals("author1", a.getName());
    }

}