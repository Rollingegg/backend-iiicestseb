package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.*;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.utils.DateUtil;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.PaperInfoVO;
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
        paper1.setPaperAbstract("test1");
        paper2.setPaperAbstract("test2");
        paper1.setDoi("doi1");
        paper2.setDoi("doi2");
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
        //System.out.println(paper1.getPublicationYear().getYear());
        Paper paper_get = paperMapper.selectByPrimaryKey(paper2.getId());
        //System.out.println(paper_get.getPublicationYear().getYear());
        //assertEquals(paper_get,paper2);
        //todo
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
        paper1.setDoi("abcde");
        paperMapper.updateByName(paper1);
        assertEquals(paperMapper.selectByPrimaryKey(paper1.getId()).getDoi(), paper1.getDoi());
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
    public void insertPublisherList() {
        Publisher publisher1 = new Publisher("nju");
        Publisher publisher2 = new Publisher("zju");
        List<Publisher> publishers = new ArrayList<>();
        publishers.add(publisher1);
        publishers.add(publisher2);
        paperMapper.insertPublisherList(publishers);
        assertEquals(paperMapper.selectPublisherByName("nju"),publisher1);
        assertEquals(paperMapper.selectPublisherById(publisher2.getId()),publisher2);
    }


    @Test
    public void insertConferenceList() {
        Conference conference1 = new Conference("ieee");
        Conference conference2 = new Conference("ase");
        List<Conference> conferences = new ArrayList<>();
        conferences.add(conference1);
        conferences.add(conference2);
        paperMapper.insertConferenceList(conferences);
        assertEquals(paperMapper.selectConferenceByName(conference1.getName()),conference1);
        assertEquals(paperMapper.selectConferenceById(conference2.getId()),conference2);

    }

    @Test
    public void insertPaperTermList() {
        Term term1 = new Term("test");
        Term term2 = new Term("design");
        List<Term> termList = new ArrayList<>();
        termList.add(term1);
        termList.add(term2);
        paperMapper.insertTermList(termList);
        assertEquals(paperMapper.selectTermByName(term1.getWord()),term1);
        paperMapper.insert(paper1);
        PaperTerm paperTerm1 = new PaperTerm(paper1,term1);
        PaperTerm paperTerm2 = new PaperTerm(paper1,term2);
        List<PaperTerm> paperTerms = new ArrayList<>();
        paperTerms.add(paperTerm1);
        paperTerms.add(paperTerm2);
        assertEquals(2,paperMapper.insertPaperTermList(paperTerms));
    }

    @Test
    public void insertPublishListAndSearch() {
        //publish测试
        author1.setName("hxd");
        author2.setName("jh");

        Affiliation affiliation = new Affiliation("nju");
        affiliationMapper.insert(affiliation);
        author1.setAffiliationId(affiliation.getId());
        author2.setAffiliationId(affiliation.getId());
        authorMapper.insert(author1);
        authorMapper.insert(author2);
        paperMapper.insert(paper1);
        paperMapper.insert(paper2);
        Publish publish1 = new Publish(paper1, author1);
        Publish publish2 = new Publish(paper2, author2);
        List<Publish> publishes = new ArrayList<>();
        publishes.add(publish1);
        publishes.add(publish2);
        paperMapper.insertPublishList(publishes);
//        List<String> authorList = authorMapper.getAuthorByPaperId(paper.getId());
//        assertEquals(authorList.get(0),author1.getName());
//        assertEquals(authorList.get(1),author2.getName());
        //搜索测试
        //assertEquals(paperMapper.simpleSearchPaperByType("paper_title","a").get(0).getId(),paper1.getId());
        //assertEquals(paperMapper.simpleSearchPaperByType("author.name","hxd").get(0).getId(),paper1.getId());
        assertEquals(paperMapper.simpleSearchPaperAll("doi",50).size(), 0);

        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
        advancedSearchForm.setAffiliationKeyword("nju");
        advancedSearchForm.setAuthorKeyword("hxd");
    }
    @Test
    public void simpleSearchPaperByType() {
        List<PaperInfoVO> paperInfoVOS;
        paperInfoVOS = paperMapper.simpleSearchPaperByType("t1.word","program diagnostics",1);
        System.out.println(paperInfoVOS.size());
        for (PaperInfoVO x:paperInfoVOS){
            System.out.println("test");
            System.out.println(x);
            System.out.println("testend");
        }
    }

    @Test
    public void simpleSearchPaperAll() {

//        List<PaperInfoVO> paperInfoVOS = new ArrayList<>();
//        paperInfoVOS = paperMapper.simpleSearchPaperAll("ta",50);
//        for (PaperInfoVO x:paperInfoVOS){
//            System.out.println("test");
//            System.out.println(x);
//            System.out.println("testend");
//        }
    }

    @Test
    public void advancedSearch() {
        List<PaperInfoVO> paperInfoVOS = new ArrayList<>();

        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
        advancedSearchForm.setDoiKeyword("a");
        advancedSearchForm.setAuthorKeyword("a");
        advancedSearchForm.setAffiliationKeyword("a");
        advancedSearchForm.setPaperTitleKeyword("a");
        advancedSearchForm.setPaperAbstractKeyword("pr");
        advancedSearchForm.setTermKeyword("g");
        paperInfoVOS = paperMapper.advancedSearch(advancedSearchForm,50);
        for (PaperInfoVO x:paperInfoVOS){
            System.out.println("test");
            System.out.println(x);
            System.out.println("testend");
        }


    }
}