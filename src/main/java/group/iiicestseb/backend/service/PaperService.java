package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.vo.PaperDetail;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;

import java.util.Collection;

/**
 * @author jh
 * @date 2020/3/29
 */
public interface PaperService {

    /**
     * 获取文献详情
     *
     * @param paperId 文献id
     * @return 文献详情
     */
    PaperDetail findPaperDetail(Integer paperId);

    /**
     * 获取指定文献的相关文献
     *
     * @param paperId 文献id
     * @param num 最大数量
     * @return 相关文献概览
     */
    Collection<PaperOverview> getRecommendPapers(Integer paperId, Integer num);

    /**
     * 获取指定文献的作者
     *
     * @param paperId 文献id
     * @param num 最大数量
     * @return 相关作者概览
     */
    Collection<AuthorInfoVO> getRecommendAuthors(Integer paperId, Integer num);

    /**
     * 获取指定文献的相关机构
     *
     * @param paperId 文献id
     * @param num 最大数量
     * @return 相关机构概览
     */
    Collection<Affiliation> getRecommendAffiliations(Integer paperId, Integer num);
}
