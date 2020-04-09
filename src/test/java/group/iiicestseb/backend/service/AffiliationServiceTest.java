//package group.iiicestseb.backend.service;
//
//import group.iiicestseb.backend.mapper.AffiliationMapper;
//import group.iiicestseb.backend.serviceImpl.AffiliationServiceImpl;
//import group.iiicestseb.backend.vo.affiliation.AffiliationInfoVO;
//import org.easymock.*;
//import org.junit.*;
//import org.springframework.transaction.annotation.Transactional;
//
//import static org.easymock.EasyMock.*;
//
//
///**
// * @author wph
// */
//@Transactional
//public class AffiliationServiceTest extends EasyMockSupport{
//
//    @Rule
//    public EasyMockRule rule = new EasyMockRule(this);
//
//    @Mock
//    private AffiliationMapper affiliationMapper;
//
//    @TestSubject
//    private AffiliationService affiliationService = new AffiliationServiceImpl();
//
//    @Test
//    public void getAffiliationInfo() {
//        Affiliation affiliation = new Affiliation();
//        affiliation.setName("nju");
//        affiliation.setId(1);
//        EasyMock.expect(affiliationMapper.selectByName("nju")).andReturn(affiliation);
//        replay(affiliationMapper);
//        AffiliationInfoVO result = affiliationService.getAffiliationInfo("nju");
//        Assert.assertEquals(result.getId(),(Integer) 1);
//        Assert.assertEquals(result.getName(),"nju");
//        verify(affiliationMapper);
//    }
//
//    @Test
//    public void selectAffiliationById(){
//        Affiliation affiliation = new Affiliation();
//        affiliation.setName("nju");
//        affiliation.setId(1);
//        EasyMock.expect(affiliationMapper.selectByPrimaryKey(1)).andReturn(affiliation);
//        replay(affiliationMapper);
//        Affiliation result = affiliationService.selectAffiliationById(1);
//        Assert.assertEquals(result.getId(),(Integer) 1);
//        Assert.assertEquals(result.getName(),"nju");
//        verify(affiliationMapper);
//    }
//}