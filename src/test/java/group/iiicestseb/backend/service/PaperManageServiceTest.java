package group.iiicestseb.backend.service;

import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.serviceImpl.PaperManageServiceImpl;
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
        paperMapper.deleteById(1);
        EasyMock.expectLastCall();
        replayAll();
        try {
            paperManageService.deletePaperById(1);
        } catch (Exception e) {
            fail();
        }
        verifyAll();
    }
}