package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Conference;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Publisher;
import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.vo.PaperInfoVO;
import org.easymock.*;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;


@Transactional
public class PaperManageServiceTest extends EasyMockSupport {
    @Rule
    public EasyMockRule rule = new EasyMockRule(this);

    @Mock
    private PaperMapper paperMapper;

    @TestSubject
    private PaperManageService paperManageService;

    @Test
    public void deletePaperById() {
        paperMapper.deleteByPrimaryKey(1);
        replayAll();
        paperManageService.deletePaperById(1);
        verifyAll();
    }

    @Test
    public void deletePaperByName() {
        paperMapper.deleteByName("seciii");
        replayAll();
        paperManageService.deletePaperById(2);
        verifyAll();
    }

    @Test
    public void updatePaperByName() {
        paperMapper.deleteByName("seciii");
        replayAll();
        paperManageService.deletePaperByName("seciii");
        verifyAll();
    }

    @Test
    public void updatePaperById() {

        paperMapper.updateByPrimaryKey(new Paper());
        replayAll();
        paperManageService.updatePaperById(new PaperForm());
        verifyAll();
    }

    @Test
    public void getPaperInfoVO() {
        Paper paper = new Paper();
        paper.setId(1);
        EasyMock.expect(paperMapper.selectByName("seciii")).andReturn(paper);
        EasyMock.expect(paperMapper.selectPublisherById(1)).andReturn(new Publisher(1,"nju"));
        EasyMock.expect(paperMapper.selectConferenceById(1)).andReturn(new Conference(1,"ieee"));
        replayAll();
        PaperInfoVO paperInfoVO = paperManageService.getPaperInfoVO("seciii");
        assertEquals("nju",paperInfoVO.getPublisherName());
        assertEquals("seciii",paperInfoVO.getConferenceName());
        verifyAll();
    }
}