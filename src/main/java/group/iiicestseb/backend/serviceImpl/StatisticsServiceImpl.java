package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.factory.PaperFactory;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.statistics.PaperCountPerYearVO;
import group.iiicestseb.backend.vo.term.TermWithCountVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Statistics")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource(name = "Regedit")
    private Regedit regedit;

    @Resource
    StatisticsMapper statisticsMapper;

    @Override
    public List<TermWithHotVO> findHotTerms(Integer num) {
        return statisticsMapper.selectTermsWithHotLimit(num);
    }

    @Override
    public List<TermWithHotVO> findTermWithHot(Integer termId) {
        return statisticsMapper.selectTermWithHot(termId);
    }

    @Override
    public Collection<Term> findRelativeTermsOfTerm(Integer termId, Integer max){
        Collection<Integer> termIds = statisticsMapper.selectRelativeTermsOfTerm(termId, max);
        return ((PaperService)regedit).findTermByIdBatch(termIds);
    }

    @Override
    public Collection<PaperOverview> findPapersByTermIdInScoreOrder(Integer termId, Integer max){
        Collection<Integer> paperIds = statisticsMapper.selectPapersOfTermOrderByScore(termId, max);
        Collection<Paper> papers = ((PaperService)regedit).findPapersByIdBatch(paperIds);
        return PaperFactory.toPaperOverviewBatch(papers);
    }

    @Override
    public Collection<AuthorInfoVO> findActiveAuthorsOfTerm(Integer termId, Integer max){
        Collection<Integer> authorIds = statisticsMapper.selectAuthorsOfTermOrderByActive(termId, max);
        return ((AuthorService)regedit).findAuthorInfoByIdBatch(authorIds);
    }

    @Override
    public Collection<Affiliation> findActiveAffiliationOfTerm(Integer termId, Integer max){
        Collection<Integer> affiliationIds = statisticsMapper.selectAffiliationsOfTermOrderByActive(termId, max);
        return ((AffiliationService)regedit).findAffiliationByIdBatch(affiliationIds);
    }

    @Override
    public List<AuthorHotVO> calculateMaxPublishAuthor(Integer num) {
        return statisticsMapper.selectMaxPublishAuthorLimit(num);
    }

    @Override
    public Collection<TermWithCountVO> getAuthorHotTerm(int id, int limit) {
        //todo 测试没写
        return statisticsMapper.getAuthorHotTerm(id,limit);
    }

    @Override
    public Collection<TermWithCountVO> getAffiliationHotTerm(int id, int limit) {
        //todo 测试没写
        return statisticsMapper.getAffiliationHotTerm(id,limit);
    }

    @Override
    public Collection<PaperCountPerYearVO> getAffiliationPublishCountPerYear(int id) {
        //todo 测试没写
        return statisticsMapper.getAffiliationPublishCountPerYear(id);
    }

    @Override
    public Collection<PaperCountPerYearVO> getAuthorPublishCountPerYear(int id) {
        //todo 测试没写
        return statisticsMapper.getAuthorPublishCountPerYear(id);
    }

}