package group.iiicestseb.backend.service;


import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.serviceImpl.PaperManageServiceImpl;
import org.easymock.EasyMockSupport;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.fail;

@Transactional
public class PaperManageServiceTest extends EasyMockSupport {

    @Mock
    private PaperMapper paperMapper;

    @InjectMocks
    private PaperManageService paperManageService = new PaperManageServiceImpl();


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);


    }

    @Test
    public void deletePaperById() {
        Mockito.when(paperMapper.deleteById(1)).thenReturn(1);
        try {
            paperManageService.deletePaperById(1);
        } catch (Exception e) {
            fail();
        }
    }
}