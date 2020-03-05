package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;

import group.iiicestseb.backend.entity.Publish;
import group.iiicestseb.backend.utils.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperMapperTest {
    @Resource
    private PaperMapper paperMapper;
    private Paper paper1 = new Paper();
    private Paper paper2 = new Paper();
    private List<Paper> papers = new ArrayList<>();

    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private AffiliationMapper affiliationMapper;
    private Author author1 = new Author();
    private Author author2 = new Author();
    @Before
    public void setUp() throws Exception {
        paper1.setPaperTitle("a");
        paper2.setPaperTitle("b");
        paper1.setPublicationYear(DateUtil.parseYear("2000"));
        papers.add(paper1);
        papers.add(paper2);
    }

    @Test
    public void deleteByPrimaryKey() {
        paperMapper.insert(paper1);
        assertEquals(1,paperMapper.deleteByPrimaryKey(paper1.getId()));
        assertEquals(0,paperMapper.deleteByPrimaryKey(paper1.getId()));

        
    }

    @Test
    public void insert() {
        paperMapper.insert(paper1);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()),paper1);
    }

    @Test
    public void insertPaperList() {
        assertEquals(paperMapper.insertPaperList(papers),2);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()),paper1);
        System.out.println(paper1.getPublicationYear().getYear());
        Paper paper_get = paperMapper.selectByPrimaryKey(paper2.getId());
        System.out.println(paper_get.getPublicationYear().getYear());
        assertEquals(paper_get,paper2);
    }

    @Test
    public void selectByPrimaryKey() {
        paperMapper.insert(paper1);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()),paper1);
    }

    @Test
    public void updateByPrimaryKey() {
        paperMapper.insert(paper1);
        paper1.setPaperTitle("test");

        paperMapper.updateByPrimaryKey(paper1);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()),paper1);
    }

    @Test
    public void deleteByName() {
        paperMapper.insert(paper1);
        assertEquals(1,paperMapper.deleteByName(paper1.getPaperTitle()));
        assertEquals(0,paperMapper.deleteByName(paper1.getPaperTitle()));
    }

    @Test
    public void updateByName() {
        paperMapper.insert(paper1);
        paper1.setPaperTitle("test");
        paperMapper.updateByPrimaryKey(paper1);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()),paper1);
    }

    @Test
    public void selectByName() {
        paperMapper.insert(paper1);
        assertEquals(paperMapper.selectByName(paper1.getPaperTitle()),paper1);
    }

    @Test
    public void selectByNameAndYear() {
        paperMapper.insert(paper1);
        assertEquals(paperMapper.selectByNameAndYear("a",DateUtil.parseYear("2000")),paper1);
    }

    @Test
    public void selectPublisherById() {
        author1.setId(1);
        author2.setId(2);
        author1.setName("hxd");
        author2.setName("jh");
        authorMapper.insert(author1);
        authorMapper.insert(author2);
        Paper paper = new Paper();
        paperMapper.insert(paper);
        Publish publish1 = new Publish(paper,author1);
        Publish publish2 = new Publish(paper,author2);
        List<Publish> publishes = new ArrayList<>();
        publishes.add(publish1);
        publishes.add(publish2);
        paperMapper.insertPublishList(publishes);
        List<String> authorList = authorMapper.getAuthorByPaperId(paper.getId());
        assertEquals(authorList.get(0),author1.getName());
        assertEquals(authorList.get(1),author2.getName());
    }

    @Test
    public void selectPublisherByName() {
    }

    @Test
    public void insertPublisherList() {
    }

    @Test
    public void selectConferenceById() {
    }

    @Test
    public void selectConferenceByName() {
    }

    @Test
    public void insertConferenceList() {
    }

    @Test
    public void selectTermByName() {
    }

    @Test
    public void insertTermList() {
    }

    @Test
    public void insertPaperTermList() {
    }

    @Test
    public void insertPublishList() {
    }

    @Test
    public void simpleSearchPaperByType() {
    }

    @Test
    public void simpleSearchPaperAll() {
    }

    @Test
    public void advancedSearch() {
    }
}