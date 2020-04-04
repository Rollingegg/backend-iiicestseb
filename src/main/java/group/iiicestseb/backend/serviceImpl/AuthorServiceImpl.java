package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.entity.PaperStatistics.AuthorPaperCites;
import group.iiicestseb.backend.factory.AuthorFactory;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.AuthorStatisticsMapper;
import group.iiicestseb.backend.mapper.PaperAuthorMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.vo.author.AuthorBasicInfoVO;
import group.iiicestseb.backend.vo.author.AuthorHotInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Author")
@Transactional(rollbackFor = Exception.class)
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;
    @Resource
    private AuthorStatisticsMapper authorStatisticsMapper;

    @Override
    public Author findAuthorByName(String name) {
        return authorMapper.selectByName(name);
    }

    @Override
    public void insertAuthorList(List<Author> authorList) {
        if (authorList.isEmpty()) {
            return;
        }
        saveBatch(authorList);
    }

    @Override
    public Collection<Author> findAuthorByIdBatch(Collection<Integer> ids) {
        return this.listByIds(ids);
    }

    @Override
    public Collection<AuthorInfoVO> findAuthorInfoByIdBatch(Collection<Integer> ids) {
        return authorMapper.selectAuthorInfoByIdBatch(ids);
    }

    @Override
    public Collection<AuthorHotInAffiliationVO> selectHotAuthorByAffiliationId(Integer id, Integer limit) {
        return authorStatisticsMapper.selectHotAuthorByAffiliationId(id, limit);
    }

    @Override
    public List<AuthorInAffiliationVO> selectAllAuthorByAffiliationId(Integer id) {
        List<Author> authorList = authorMapper.selectAllAuthorByAffiliationId(id);
        List<AuthorInAffiliationVO> authorInAffiliationVOList = new ArrayList<>();
        for (Author next : authorList) {
            authorInAffiliationVOList.add(AuthorFactory.toAuthorVO(next));
        }
        return authorInAffiliationVOList;
    }

    @Override
    public AuthorBasicInfoVO getAuthorBasicInfoByAuthorId(Integer id) {
        return authorMapper.selectAuthorBasicInfoById(id);
    }

    @Override
    public Collection<AuthorInfoVO> getAuthorPartner(Integer id, Integer limit) {
        return authorMapper.selectPartnerById(id, limit);
    }

    @Override
    public AuthorStatistics getAuthorStatisticsByAuthorId(Integer authorId) {
        return authorStatisticsMapper.selectByAuthorId(authorId);
    }

    @Override
    public Collection<AuthorStatistics> getAuthorStatisticsByAuthorIdBatch(Collection<Integer> authorIds) {
        return authorStatisticsMapper.selectByAuthorIdBatch(authorIds);
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
