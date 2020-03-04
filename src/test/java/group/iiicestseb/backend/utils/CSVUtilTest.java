package group.iiicestseb.backend.utils;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.junit.Assert.fail;

/**
 * @author jh
 * @date 2020/3/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CSVUtilTest {

    @Resource
    private AffiliationMapper affiliationMapper;
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private PaperMapper paperMapper;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        System.out.println("start");
    }

    @Test
    public void analyzeExistedCSVSuccess() {
        String filename = "Standard.csv";
        CSVUtil.analyzeExistedCSV(filename);
        Author aut = authorMapper.selectByName("author2");
        Assert.assertNotNull(aut.getAffiliationId());
        Affiliation aff = affiliationMapper.selectByName("affiliation1");
        Assert.assertNotNull(aff.getId());
        Paper paper = paperMapper.selectByNameAndYear("paper title", DateUtil.parseYear("2013"));
        Assert.assertEquals("xx.xxxx/xx.xxxx.xxxxxxxxx", paper.getDoi());
        Assert.assertNotNull(paper.getId());
        Conference c = paperMapper.selectConferenceByName("Standard");
        Assert.assertNotNull(c.getId());
        Publisher publisher = paperMapper.selectPublisherByName("IEEE");
        Assert.assertNotNull(publisher.getId());
        Term t = paperMapper.selectTermByName("term2");
        Assert.assertNotNull(t.getId());
    }

    @Test
    public void analyzeExistedCSVHeaderError() {
        String filename = "HeaderError.csv";
        try {
            CSVUtil.analyzeExistedCSV(filename);
            fail();
        } catch (CSVUtil.CSVException e) {
            Assert.assertEquals(CSVUtil.HEADER_FORMAT_ERROR, e.getMessage());
        }
    }

    @Test
    public void analyzeExistedCSVLineContentError() {
        String filename = "LineErrorAt5.csv";
        try {
            CSVUtil.analyzeExistedCSV(filename);
            fail();
        } catch (CSVUtil.CSVException e) {
            Assert.assertEquals(CSVUtil.COL_FORMAT_ERROR + 5, e.getMessage());
        }
    }

    @Test
    public void analyzeUploadedCSVSuccess() throws IOException {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/Standard.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), "Standard.csv", "multipart/form-data", fileInput);
        CSVUtil.analyzeUploadedCSV(multipartFile);
        Author aut = authorMapper.selectByName("author2");
        Assert.assertNotNull(aut.getAffiliationId());
        Affiliation aff = affiliationMapper.selectByName("affiliation1");
        Assert.assertNotNull(aff.getId());
        Paper paper = paperMapper.selectByNameAndYear("paper title", DateUtil.parseYear("2013"));
        Assert.assertEquals("xx.xxxx/xx.xxxx.xxxxxxxxx", paper.getDoi());
        Assert.assertNotNull(paper.getId());
        Conference c = paperMapper.selectConferenceByName("Standard");
        Assert.assertNotNull(c.getId());
        Publisher publisher = paperMapper.selectPublisherByName("IEEE");
        Assert.assertNotNull(publisher.getId());
        Term t = paperMapper.selectTermByName("term2");
        Assert.assertNotNull(t.getId());
    }

    @Test
    public void analyzeUploadedCSVHeaderError() throws IOException {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/HeaderError.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), "HeaderError.csv", "multipart/form-data", fileInput);
        try {
            CSVUtil.analyzeUploadedCSV(multipartFile);
            fail();
        } catch (CSVUtil.CSVException e) {
            Assert.assertEquals(CSVUtil.HEADER_FORMAT_ERROR, e.getMessage());
        }
    }

    @Test
    public void analyzeUploadedCSVLineContentError() throws IOException {
        String path = this.getClass().getResource("/").getPath();
        String filename = path + "csv/LineErrorAt5.csv";
        File file = new File(filename);
        FileInputStream fileInput = new FileInputStream(file);
        MultipartFile multipartFile = new MockMultipartFile(file.getName(), "LineErrorAt5.csv", "multipart/form-data", fileInput);
        try {
            CSVUtil.analyzeUploadedCSV(multipartFile);
            fail();
        } catch (CSVUtil.CSVException e) {
            Assert.assertEquals(CSVUtil.COL_FORMAT_ERROR + 5, e.getMessage());
        }
    }
}