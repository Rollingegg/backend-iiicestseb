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
    private AffiliationMapper affiliationMapper
;
    @Test
    public void deleteByPrimaryKey() {
        affiliationMapper.deleteByPrimaryKey(1);
        assertNull( affiliationMapper.selectByPrimaryKey(1));
    }

    @Test
    public void insert() {
        Affiliation affiliation = new Affiliation();
        affiliation.setId(2);
        affiliation.setName("2");
        affiliationMapper.insert(affiliation);
        assertNull(affiliationMapper.selectByPrimaryKey(2));
    }


    @Test
    public void selectByPrimaryKey() {
        assertEquals("1",affiliationMapper.selectByPrimaryKey(1).getName());
    }


    @Test
    public void updateByPrimaryKey() {
        Affiliation affiliation = new Affiliation();
        affiliation.setId(1);
        affiliation.setName("333");
        affiliationMapper.updateByPrimaryKey(affiliation);
        //assertEquals("333",affiliationMapper.selectByPrimaryKey(1));

    }

    @Test
    public void selectByName() {

    }
}