package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.factory.AffiliationFactory;
import group.iiicestseb.backend.factory.PaperFactory;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.affiliation.AffiliationActiveInTerm;
import group.iiicestseb.backend.vo.affiliation.AffiliationActiveInTerm.AffWithScore;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.statistics.GeneralCountPerYearVO;
import group.iiicestseb.backend.vo.term.TermWithCountVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.hibernate.validator.internal.util.CollectionHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    public Collection<Term> findRelativeTermsOfTerm(Integer termId, Integer max) {
        Collection<Integer> termIds = statisticsMapper.selectRelativeTermsOfTerm(termId, max);
        return ((PaperService) regedit).findTermByIdBatch(termIds);
    }

    @Override
    public Collection<PaperOverview> findPapersByTermIdInScoreOrder(Integer termId, Integer max) {
        Collection<Integer> paperIds = statisticsMapper.selectPapersOfTermOrderByScore(termId, max);
        Collection<Paper> papers = ((PaperService) regedit).findPapersByIdBatch(paperIds);
        return PaperFactory.toPaperOverviewBatch(papers);
    }

    @Override
    public Collection<AuthorInfoVO> findActiveAuthorsOfTerm(Integer termId, Integer max) {
        Collection<Integer> authorIds = statisticsMapper.selectAuthorsOfTermOrderByActive(termId, max);
        return ((AuthorService) regedit).findAuthorInfoByIdBatch(authorIds);
    }

    @Override
    public Collection<AffiliationActiveInTerm> findActiveAffiliationOfTerm(Integer termId, Integer max) {
        Collection<AffWithScore> temp = statisticsMapper.selectAffiliationsOfTermForActive(termId, max);
        Map<Integer, AffWithScore> id_AffScores = new HashMap<>(temp.size() * 2);
        for (AffWithScore as : temp) {
            id_AffScores.put(as.getId(), as);
        }
        Collection<Affiliation> affiliations = ((AffiliationService) regedit).findAffiliationByIdBatch(id_AffScores.keySet());
        Collection<AffiliationActiveInTerm> activeInTerms = AffiliationFactory.packageAffiliationActiveInTerm(id_AffScores, affiliations);
        List<AffiliationActiveInTerm> results = CollectionHelper.newArrayList(activeInTerms);
        results.sort((o1, o2) ->
                o1.getPaperNum() > o2.getPaperNum() ? 1 : o1.getPaperNum().equals(o2.getPaperNum()) ? 0 : -1);
        return results;
    }

    @Override
    public List<AuthorHotVO> calculateMaxPublishAuthor(Integer num) {
        return statisticsMapper.selectMaxPublishAuthorLimit(num);
    }

    @Override
    public Collection<TermWithCountVO> getAuthorHotTerm(int id, int limit) {
        return statisticsMapper.getAuthorHotTerm(id, limit);
    }

    @Override
    public Collection<TermWithCountVO> getAffiliationHotTerm(int id, int limit) {
        return statisticsMapper.getAffiliationHotTerm(id, limit);
    }

    @Override
    public Collection<GeneralCountPerYearVO> getAffiliationPublishCountPerYear(int id) {
        return statisticsMapper.getAffiliationPublishCountPerYear(id);
    }

    @Override
    public Collection<GeneralCountPerYearVO> getAuthorPublishCountPerYear(int id) {
        return statisticsMapper.getAuthorPublishCountPerYear(id);
    }

    @Override
    public Collection<GeneralCountPerYearVO> getTermCountPerYear(int id) {
        return statisticsMapper.getTermCountPerYear(id);
    }
}