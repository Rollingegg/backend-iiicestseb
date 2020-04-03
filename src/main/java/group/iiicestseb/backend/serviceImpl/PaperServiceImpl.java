package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.*;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.service.ConferenceService;
import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.vo.PaperDetail;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.factory.PaperFactory;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jh
 * @date 2020/3/29
 */
@Service("Paper")
public class PaperServiceImpl implements PaperService {

    @Resource(name = "Regedit")
    private Regedit regedit;

    @Resource
    private PaperMapper paperMapper;
    @Resource
    private TermMapper termMapper;
    @Resource
    private PaperTermMapper paperTermMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;
    @Resource
    private ReferenceMapper referenceMapper;

    @Override
    public PaperDetail findPaperDetail(Integer paperId) {
        Paper paper = paperMapper.selectById(paperId);
        Conference conference = ((ConferenceService) regedit).findConferenceById(paper.getConferenceId());
        List<Author> authorList = paperAuthorMapper.findAuthorsByPaperId(paperId);
        List<Term> termList = termMapper.selectByPaperId(paperId);
        List<Reference> referenceList = referenceMapper.selectByArticleId(paper.getArticleId());
        return PaperFactory.packageDetail(paper, authorList, conference, termList, referenceList);
    }

    @Override
    public Collection<Paper> findPapersByIdBatch(Collection<Integer> paperIds) {
        return paperMapper.selectBatchIds(paperIds);
    }

    /**
     * 这里用相同受控关键词的数量来排序
     */
    @Override
    public Collection<PaperOverview> getRecommendPapers(Integer paperId, Integer num) {
        List<Integer> similarPaperIDs = paperTermMapper.selectSimilarPaperIdsByPaperId(paperId, num);
        List<Paper> paperList = paperMapper.selectBatchIds(similarPaperIDs);
        return PaperFactory.toPaperOverviewBatch(paperList);
    }

    /**
     * 这里先返回文章作者
     */
    @Override
    public Collection<AuthorInfoVO> getRecommendAuthors(Integer paperId, Integer num) {
        Collection<Integer> similarAuthorIDs = paperAuthorMapper.selectSimilarAuthorIdsByPaperId(paperId, num);
        return ((AuthorService)regedit).findAuthorInfoByIdBatch(similarAuthorIDs);
    }

    /**
     * 这里先返回原文献作者的机构
     */
    @Override
    public Collection<Affiliation> getRecommendAffiliations(Integer paperId, Integer num) {
        Collection<AuthorInfoVO> authors = getRecommendAuthors(paperId, num);
        Collection<Integer> affiliationIds = new LinkedList<>();
        for (AuthorInfoVO a : authors) {
            affiliationIds.add(a.getAffiliationId());
        }
        return ((AffiliationService)regedit).findAffiliationByIdBatch(affiliationIds);
    }

    @Override
    public Collection<Term> findTermByIdBatch(Collection<Integer> termIds) {
        return termMapper.selectBatchIds(termIds);
    }


}
