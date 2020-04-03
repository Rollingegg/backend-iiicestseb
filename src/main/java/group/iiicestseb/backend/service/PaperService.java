package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.vo.PaperDetail;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.paper.PaperBasicVO;
import group.iiicestseb.backend.vo.paper.SearchResultVO;

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
     * 批量查找论文
     * @param paperIds 论文id集合
     * @return 论文实体集合
     */
    Collection<Paper> findPapersByIdBatch(Collection<Integer> paperIds);

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

    /**
     * 搜索机构最近发表的文献
     * @param id 机构id
     * @param limit 搜索个数
     * @return 机构最近发表的文献列表
     */
    Collection<PaperBasicVO> getAffiliationRecentlyPublish(Integer id, Integer limit);


    /**
     * 搜索机构发表的所有文献
     * @param id 机构id
     * @return 机构所有发表的文献列表
     */
    Collection<SearchResultVO> getAffiliationAllPublish(Integer id);

    /**
     * 获取作者最近所有文献
     * @param id 作者id
     * @param limit 搜索数
     * @return 作者最近论文
     */
    Collection<PaperBasicVO> getAuthorRecentPaper(Integer id,int limit);


    /**
     * 获取作者所有文献
     * @param id 作者id
     * @return 作者所有文献
     */
    Collection<SearchResultVO> getAuthorAllPaper(Integer id);


    /**
     * 通过id批量查找术语
     *
     * @param termIds 术语id集合
     * @return 术语id
     */
    Collection<Term> findTermByIdBatch(Collection<Integer> termIds);
}
