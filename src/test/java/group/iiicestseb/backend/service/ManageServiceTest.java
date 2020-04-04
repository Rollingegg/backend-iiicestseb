package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.serviceImpl.ManageServiceImpl;
import group.iiicestseb.backend.utils.JSONUtil;
import org.easymock.EasyMockSupport;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import static org.junit.Assert.fail;

@Transactional
@SpringBootTest
@RunWith(SpringRunner.class)
public class ManageServiceTest extends EasyMockSupport {

    @Mock
    private PaperMapper paperMapper;

    @InjectMocks
    private ManageService manageServiceMock = new ManageServiceImpl();

    @Resource(name = "Manage")
    private ManageService manageService;

    @Resource(name = "Regedit")
    private Regedit regedit;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        JSONUtil.loadTestData();

    }

    @Test
    public void deletePaperById() {
        Mockito.when(paperMapper.deleteById(1)).thenReturn(1);
        try {
            manageServiceMock.deletePaperById(1);
        } catch (Exception e) {
            fail();
        }
    }

    @Test
    public void reComputeAuthorStatistics() {
        regedit.reComputeAuthorStatistics();
        Author author1 = regedit.findAuthorByName("author1");
        Author author3 = regedit.findAuthorByName("author3");
        AuthorStatistics as1 = regedit.getAuthorStatisticsByAuthorId(author1.getId());
        AuthorStatistics as3 = regedit.getAuthorStatisticsByAuthorId(author3.getId());
        Assert.assertEquals(Integer.valueOf(1), as1.getHIndex());
        Assert.assertEquals(Integer.valueOf(2), as1.getGIndex());
        Assert.assertEquals(Double.valueOf(8.0), as1.getAvgCite());
        Assert.assertEquals(Integer.valueOf(2), as1.getPaperNum());
        Assert.assertEquals(Integer.valueOf(1), as1.getAsePaperNum());
        Assert.assertEquals(Integer.valueOf(1), as1.getIcsePaperNum());
        Assert.assertEquals(Integer.valueOf(2), as3.getHIndex());
        Assert.assertEquals(Integer.valueOf(3), as3.getGIndex());
        Assert.assertEquals(Double.valueOf(10.333333333333334), as3.getAvgCite());
        Assert.assertEquals(Integer.valueOf(3), as3.getPaperNum());
        Assert.assertEquals(Integer.valueOf(2), as3.getAsePaperNum());
        Assert.assertEquals(Integer.valueOf(1), as3.getIcsePaperNum());
    }
}