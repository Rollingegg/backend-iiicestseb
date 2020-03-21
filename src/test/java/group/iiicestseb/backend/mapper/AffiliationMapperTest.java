package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author wph
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AffiliationMapperTest {
    @Autowired
    private AffiliationMapper affiliationMapper;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void name() {
        Affiliation affiliation = new Affiliation();
        affiliation.setName("test");
        affiliationMapper.save(affiliation);
        affiliationMapper.save(affiliation);
        affiliationMapper.save(affiliation);
        affiliationMapper.save(affiliation);
    }

    //    @Test
//    public void deleteByPrimaryKey() {
//        affiliationMapper.insert(affiliation);
//        assertEquals(1,affiliationMapper.deleteByPrimaryKey(affiliation.getId()));
//        assertEquals(0,affiliationMapper.deleteByPrimaryKey(affiliation.getId()));
//
//
//    }
//
//    @Test
//    public void insert() {
//        affiliationMapper.insert(affiliation);
//        assertEquals(affiliation,affiliationMapper.selectByPrimaryKey(affiliation.getId()));
//    }
//
//    @Test
//    public void insertAffiliationList() {
//        List<Affiliation> test = new ArrayList<>();
//        test.add(affiliation);
//        test.add(affiliation2);
//        assertEquals(2,affiliationMapper.insertAffiliationList(test));
//        assertEquals(affiliation,affiliationMapper.selectByPrimaryKey(affiliation.getId()));
//        assertEquals(affiliation2,affiliationMapper.selectByPrimaryKey(affiliation2.getId()));
//    }
//
//    @Test
//    public void selectByPrimaryKey() {
//        affiliationMapper.insert(affiliation2);
//        assertEquals(affiliation2,affiliationMapper.selectByPrimaryKey(affiliation2.getId()));
//    }
//
//    @Test
//    public void updateByPrimaryKey() {
//        affiliationMapper.insert(affiliation);
//        affiliation.setName("test_init");
//        affiliationMapper.updateByPrimaryKey(affiliation);
//        assertEquals(affiliation,affiliationMapper.selectByPrimaryKey(affiliation.getId()));
//    }
//
//    @Test
//    public void selectByName() {
//        affiliation.setName("gogo");
//        affiliationMapper.insert(affiliation);
//        assertEquals(affiliation,affiliationMapper.selectByName(affiliation.getName()));
//    }
}