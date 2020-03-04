package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.swing.*;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AffiliationMapperTest {
    @Resource
    private AffiliationMapper affiliationMapper;

    @Test
    public void deleteByPrimaryKey() {

    }

    @Test
    public void insert() {

    }


    @Test
    public void selectByPrimaryKey() {
        assertEquals("1",affiliationMapper.selectByPrimaryKey(1).getName());
    }


    @Test
    public void updateByPrimaryKey() {


    }

    @Test
    public void selectByName() {

    }
}