package group.iiicestseb.backend.service;

import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.serviceImpl.StatisticsServiceImpl;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@Transactional
public class StatisticsServiceTest {

    @InjectMocks
    StatisticsService statisticsService = new StatisticsServiceImpl();

    @Mock
    StatisticsMapper statisticsMapper;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void calculateHotTerms() {
        ArrayList<TermWithHotVO> termWithHotVOArrayList = new ArrayList<>();
        TermWithHotVO termWithHotVO_1 = new TermWithHotVO(1,"a",2);
        TermWithHotVO termWithHotVO_2 = new TermWithHotVO(2,"b",555);
        termWithHotVOArrayList.add(termWithHotVO_1);
        termWithHotVOArrayList.add(termWithHotVO_2);
        Mockito.when(statisticsMapper.selectTermsWithHotLimit(5)).thenReturn(termWithHotVOArrayList);
        assertEquals(termWithHotVOArrayList,statisticsMapper.selectTermsWithHotLimit(5));
        Mockito.verify(statisticsMapper).selectTermsWithHotLimit(5);
    }

    @Test
    public void calculateMaxPublishAuthor() {
        List<AuthorHotVO> authorHotVOList = new ArrayList<>();
        AuthorHotVO authorHotVO_1 = new AuthorHotVO(1,"jh",1,"nju",100);
        AuthorHotVO authorHotVO_2 = new AuthorHotVO(2,"hxd",1,"zju",200);
        authorHotVOList.add(authorHotVO_1);
        authorHotVOList.add(authorHotVO_2);
        Mockito.when(statisticsMapper.selectMaxPublishAuthorLimit(5)).thenReturn(authorHotVOList);
        assertEquals(authorHotVOList,statisticsMapper.selectMaxPublishAuthorLimit(5));
        Mockito.verify(statisticsMapper).selectMaxPublishAuthorLimit(5);
    }
}