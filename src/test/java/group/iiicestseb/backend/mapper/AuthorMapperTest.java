package group.iiicestseb.backend.mapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wph
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AuthorMapperTest {
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private AffiliationMapper affiliationMapper;
    private Author author1 = new Author();
    private Author author2 = new Author();

    private Affiliation affiliation = new Affiliation("nju");

    @Before
    public void setUp() throws Exception {
        affiliationMapper.insert(affiliation);
        author1.setName("hxd");
        author2.setName("jh");
        author1.setAffiliationId(affiliation.getId());
        author2.setAffiliationId(affiliation.getId());
    }

    @Test
    public void deleteByPrimaryKey() {
        authorMapper.insert(author1);
        assertEquals(authorMapper.deleteByPrimaryKey(author1.getId()),1);
        assertEquals(authorMapper.deleteByPrimaryKey(author1.getId()),0);
    }

    @Test
    public void insert() {

        assertEquals(1,authorMapper.insert(author1));
        assertEquals(author1,authorMapper.selectByPrimaryKey(author1.getId()));
    }

    @Test
    public void insertAuthorList() {
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);

        assertEquals(2,authorMapper.insertAuthorList(authors));
        assertEquals(author1,authorMapper.selectByPrimaryKey(author1.getId()));
        assertEquals(author2,authorMapper.selectByPrimaryKey(author2.getId()));
    }

    @Test
    public void selectByPrimaryKey() {
        authorMapper.insert(author1);
        assertEquals(author1,authorMapper.selectByPrimaryKey(author1.getId()));
    }

    @Test
    public void updateByPrimaryKey() {
        authorMapper.insert(author1);
        author1.setName("gogo");
        authorMapper.updateByPrimaryKey(author1);
        assertEquals(author1,authorMapper.selectByPrimaryKey(author1.getId()));
    }

    @Test
    public void selectByName() {
        authorMapper.insert(author1);
        assertEquals(author1,authorMapper.selectByName(author1.getName()));
    }

    @Test
    public void getAuthorByPaperId() {
        //assertEquals(1,2);
        //todo 写测试
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
}