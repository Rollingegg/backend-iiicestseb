package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.statistics.GeneralCountPerYearVO;
import group.iiicestseb.backend.vo.term.TermWithCountVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;

import java.util.Collection;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface StatisticsService {
    /**
     * 计算并返回最热门的num个术语
     * 热度按为所有文章中出现的总次数
     *
     * @param num num个术语
     * @return 最热门的num个术语和其出现次数
     */
    List<TermWithHotVO> findHotTerms(Integer num);

    /**
     * 获得term及其热度
     * 热度按为所有文章中出现的总次数
     *
     * @param id 术语id
     * @return term及热度
     */
    List<TermWithHotVO> findTermWithHot(Integer id);

    /**
     * 获得指定术语的相关的术语
     *
     * @param termId 术语id
     * @param max 数量上限
     * @return 相关术语集合
     */
    Collection<Term> findRelativeTermsOfTerm(Integer termId, Integer max);

    /**
     * 获得指定术语的相关的论文
     *
     * @param termId 术语id
     * @param max 数量上限
     * @return 相关论文集合
     */
    Collection<PaperOverview> findPapersByTermIdInScoreOrder(Integer termId, Integer max);

    /**
     * 获得指定术语的相关作者
     *
     * @param termId 术语id
     * @param max 数量上限
     * @return 相关作者集合
     */
    Collection<AuthorInfoVO> findActiveAuthorsOfTerm(Integer termId, Integer max);

    /**
     * 获得指定术语的相关的机构
     *
     * @param termId 术语id
     * @param max 数量上限
     * @return 相关机构集合
     */
    Collection<Affiliation> findActiveAffiliationOfTerm(Integer termId, Integer max);

    /**
     * 计算并返回发表论文最多的的num个学者和其发表的论文
     *
     * @param num num个学者
     * @return 最热门的num个学者和其发表的论文
     */
    List<AuthorHotVO> calculateMaxPublishAuthor(Integer num);



    /**
     * 获取作者的热度研究方向、词云
     * @param id 作者id
     * @param limit 搜索数
     * @return 研究方向热度列表
     */
    public Collection<TermWithCountVO> getAuthorHotTerm(int id, int limit);


    /**
     * 获取机构的热度研究方向、词云
     * @param id 机构id
     * @param limit 搜索数
     * @return 研究方向热度列表
     */
    public Collection<TermWithCountVO> getAffiliationHotTerm(int id, int limit);


    /**
     * 获取机构每年发表数
     * @param id 机构id
     * @return 机构
     */
    public Collection<GeneralCountPerYearVO> getAffiliationPublishCountPerYear(int id);


    /**
     * 获取作者每年发表数
     * @param id 作者id
     * @return 作者每年发表数列表
     */
    public Collection<GeneralCountPerYearVO> getAuthorPublishCountPerYear(int id);

    /**
     * 获取术语每年相关文章数
     * @param id 术语id
     * @return 术语每年发表数列表
     */
    public Collection<GeneralCountPerYearVO> getTermCountPerYear(int id);
}