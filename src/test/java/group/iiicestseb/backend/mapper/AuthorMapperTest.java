package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.service.AuthorService;
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
    private AffiliationMapper affiliationMapper;
    private Author author1 = new Author();
    private Author author2 = new Author();

    private Affiliation affiliation = new Affiliation("nju");

    @Before
    public void setUp() throws Exception {
        affiliationMapper.insert(affiliation);
        author1.setId(1);
        author2.setId(2);
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
    }

    @Test
    public void insertAuthorList() {
        List<Author> authors = new ArrayList<>();
        authors.add(author1);
        authors.add(author2);
        assertEquals(2,authorMapper.insertAuthorList(authors));
    }

    @Test
    public void selectByPrimaryKey() {
        authorMapper.insert(author1);
        assertEquals("hxd",authorMapper.selectByPrimaryKey(author1.getId()).getName());
    }

    @Test
    public void updateByPrimaryKey() {
        authorMapper.insert(author1);
        author1.setName("gogo");
        System.out.println(author1.getId());
        assertEquals(1,authorMapper.updateByPrimaryKey(author1));
    }

    @Test
    public void selectByName() {
        authorMapper.insert(author1);
        assertEquals(author1.getId(),authorMapper.selectByName(author1.getName()).getId());
    }

    @Test
    public void getAuthorByPaperId() {
        //assertEquals(1,2);
        //todo 写测试
    }
}