package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.entity.PaperStatistics.AuthorPaperCites;
import group.iiicestseb.backend.mapper.*;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.ManageService;
import group.iiicestseb.backend.utils.JSONUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author jh
 * @date 2020/2/22
 */


@Service("Manage")
@Transactional(rollbackFor = Exception.class)
public class ManageServiceImpl extends ServiceImpl<PaperMapper, Paper> implements ManageService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private ReferenceMapper referenceMapper;
    @Resource
    private PaperTermMapper paperTermMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;
    @Resource
    private TermMapper termMapper;
    @Resource
    private AuthorStatisticsMapper authorStatisticsMapper;

    @Override
    public void deletePaperById(int id) {
        paperMapper.deleteById(id);
    }

    @Override
    public void insertPaper(Paper paper) {
        paperMapper.insert(paper);
    }

    @Override
    public void insertPaperTermList(List<PaperTerm> paperTerms) {
        if (paperTerms.isEmpty()) {
            return;
        }
        paperTermMapper.insertList(paperTerms);
    }

    @Override
    public void insertReferences(List<Reference> references) {
        if (references.isEmpty()) {
            return;
        }
        referenceMapper.insertList(references);
    }

    @Override
    public void insertPaperAuthorList(List<PaperAuthors> paperAuthors) {
        if (paperAuthors.isEmpty()) {
            return;
        }
        paperAuthorMapper.insertList(paperAuthors);
    }

    @Override
    public Paper findPaperByArticleId(Integer articleId) {
        return paperMapper.selectByArticleId(articleId);
    }

    @Override
    public Term findTermByName(String name) {
        return termMapper.selectByName(name);
    }

    @Override
    public void saveTermList(List<Term> termList) {
        if (termList.isEmpty()) {
            return;
        }
        termMapper.insertTermList(termList);
    }

    @Override
    public Integer reComputePapersScore() {
        return paperMapper.reComputePapersScore();
    }

    @Override
    public PaperStatistics findPaperStatistics(Integer paperId) {
        return paperMapper.selectPaperStatisticsByPaperId(paperId);
    }

    @Override
    public PaperStatistics updatePaperScore(Integer id) {
        paperMapper.updatePaperScore(id);
        return paperMapper.selectPaperStatisticsByPaperId(id);
    }

    @Override
    public Collection<PaperStatistics> updatePaperScoreBatch(Collection<Integer> ids) {
        paperMapper.updatePaperScoreBatch(ids);
        return paperMapper.selectPaperStatisticsByPaperIdBatch(ids);
    }

    @Override
    public Integer reComputeAuthorStatistics() {
        Collection<AuthorPaperCites> authorCites = authorStatisticsMapper.selectAllAuthorPaperCites();
        Map<Integer, List<Integer>> values = new HashMap<>();
        Map<Integer, Integer> aseCounts = new HashMap<>();
        Map<Integer, Integer> icseCounts = new HashMap<>();
        List<Integer> cites;
        int aseCount, icseCount;
        for (AuthorPaperCites apc : authorCites) {
            cites = values.computeIfAbsent(apc.getAuthorId(), k -> new LinkedList<>());
            cites.add(apc.getCite());
            if (apc.getConference().equals(JSONUtil.CONFERENCE.ASE.value())) {
                aseCount = aseCounts.computeIfAbsent(apc.getAuthorId(), k -> 0);
                aseCounts.put(apc.getAuthorId(), aseCount + 1);
            } else if (apc.getConference().equals(JSONUtil.CONFERENCE.ICSE.value())) {
                icseCount = icseCounts.computeIfAbsent(apc.getAuthorId(), k -> 0);
                icseCounts.put(apc.getAuthorId(), icseCount + 1);
            }
        }
        Collection<AuthorStatistics> authorStatistics = new LinkedList<>();
        for (Integer key : values.keySet()) {
            cites = values.get(key);
            int i = 1, total = 0;
            int h = 0, g = 0;
            for (Integer cite : cites) {
                h = cite >= i ? h + 1 : h;
                total += cite;
                g = (i ^ 2) <= total ? g + 1 : g;
                i++;
            }
            authorStatistics.add(new AuthorStatistics(key, h, g, ((double) total) / cites.size(), cites.size(), aseCounts.get(key), icseCounts.get(key)));
        }
        return authorStatisticsMapper.insertOrUpdateBatch(authorStatistics);
    }

}
