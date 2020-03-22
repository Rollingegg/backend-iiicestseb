package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.service.AffiliationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wph
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class AffiliationMapperTest {

    @Resource
    private AffiliationMapper affiliationMapper;

    @Resource
    private AffiliationService affiliationService;

    @Test
    public  void test(){
        List<Affiliation> affiliations = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            affiliations.add(new Affiliation(i,String.valueOf(i)));

            //affiliationMapper.insert(new Affiliation(i,String.valueOf(i)));
        }
        affiliationService.saveBatch(affiliations);
    }


    @Test
    public void test1() {
        List<Affiliation> affiliations = new ArrayList<>();
        for (int i = 0; i < 200000; i++) {
            affiliations.add(new Affiliation(i,String.valueOf(i)));

            //affiliationMapper.insert(new Affiliation(i,String.valueOf(i)));
        }
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 200000; i++) {
            //affiliationMapper.saveBatch(affiliations.get(0));
            affiliationMapper.saveBatch(affiliations);
        }
        long t2 = System.currentTimeMillis();
//        List<Affiliation> affiliations = new ArrayList<>();
//        for (int i = 0; i < 200000; i++) {
//            affiliations.add(new Affiliation(i,String.valueOf(i)));
//        }
//        long t1 = System.currentTimeMillis();
//        affiliationMapper.findByName("test");
//        long t2 = System.currentTimeMillis();


        System.out.println("result");
        System.out.println(t2-t1);
    }
}