package group.iiicestseb.backend.service;

import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.serviceImpl.PaperManageServiceImpl;
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
    private PaperManageService paperManageService = new PaperManageServiceImpl();

    @Test
    public void deletePaperById() {
        EasyMock.expect(paperMapper.deleteByPrimaryKey(1)).andReturn(1) ;
        replayAll();
        paperManageService.deletePaperById(1);
        verifyAll();
    }

    @Test
    public void deletePaperByName() {
        EasyMock.expect(paperMapper.deleteByName("seciii")).andReturn(1);
        replayAll();
        paperManageService.deletePaperByName("seciii");
        verifyAll();
    }

    @Test
    public void updatePaperByName() {
        EasyMock.expect(paperMapper.updateByName((Paper) EasyMock.anyObject())).andReturn(1);
        replayAll();
        paperManageService.updatePaperByName(new PaperForm());
        verifyAll();
    }

    @Test
    public void updatePaperById() {

        EasyMock.expect(paperMapper.updateByPrimaryKey((Paper) EasyMock.anyObject())).andReturn(1);
        replayAll();
        paperManageService.updatePaperById(new PaperForm());
        verifyAll();
    }

    @Test
    public void getPaperInfoVO() {
        Paper paper = new Paper();
        paper.setId(1);
        paper.setPublisherId(1);
        paper.setConferenceId(1);
        EasyMock.expect(paperMapper.selectByName("seciii")).andReturn(paper);
        EasyMock.expect(paperMapper.selectPublisherById(1)).andReturn(new Publisher(1,"nju"));
        EasyMock.expect(paperMapper.selectConferenceById(1)).andReturn(new Conference(1,"ieee"));
        replayAll();
        PaperInfoVO paperInfoVO = paperManageService.getPaperInfoVO("seciii");
        assertEquals("nju",paperInfoVO.getPublisherName());
        assertEquals("ieee",paperInfoVO.getConferenceName());
        verifyAll();
    }
}