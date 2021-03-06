package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.vo.author.AuthorBasicInfoVO;
import group.iiicestseb.backend.vo.author.AuthorHotInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.graph.Graph;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;
import java.util.List;

/**
 * @author wph
 * @date 2020/03/01
 */
public interface AuthorService {

    /**
     * 根据作者名查询作者
     *
     * @param name 作者名
     * @return 作者PO
     */
    Author findAuthorByName(String name);

    /**
     * 插入作者列表
     *
     * @param authorList 作者列表
     */
    void insertAuthorList(List<Author> authorList);

    /**
     * 通过id批量查找作者
     *
     * @param ids id集合
     * @return 作者集合
     */
    Collection<Author> findAuthorByIdBatch(Collection<Integer> ids);

    /**
     * 通过id批量查找作者
     *
     * @param ids id集合
     * @return 作者集合
     */
    Collection<AuthorInfoVO> findAuthorInfoByIdBatch(Collection<Integer> ids);


    /**
     * 根据机构id搜索该机构的热门作者
     *
     * @param id    机构id
     * @param limit 搜索个数
     * @return 作者列表
     */
    Collection<AuthorHotInAffiliationVO> selectHotAuthorByAffiliationId(Integer id, Integer limit);


    /**
     * 根据机构id搜索该机构所有作者
     *
     * @param id 机构id
     * @return 作者列表
     */
    List<AuthorInAffiliationVO> selectAllAuthorByAffiliationId(Integer id);

    /**
     * 作者详情页面获取作者基本信息
     *
     * @param id 作者id
     * @return 作者详情页面基本信息
     */
    AuthorBasicInfoVO getAuthorBasicInfoByAuthorId(Integer id);


    /**
     * 获取作者合作伙伴
     *
     * @param id    作者id
     * @param limit 搜索数
     * @return 合作伙伴列表
     */
    Collection<AuthorInfoVO> getAuthorPartner(Integer id, Integer limit);

    /**
     * 根据作者id获取作者统计信息
     *
     * @param authorId 作者id
     * @return 作者统计信息
     */
    AuthorStatistics getAuthorStatisticsByAuthorId(Integer authorId);

    /**
     * 批量根据作者id查找作者统计信息
     *
     * @param authorIds 作者id集合
     * @return 作者统计信息
     */
    Collection<AuthorStatistics> getAuthorStatisticsByAuthorIdBatch(Collection<Integer> authorIds);

    /**
     * 获取指定作者合作伙伴关系图
     * @param id 作者id
     * @param limit 上限设置
     * @return 作者合作伙伴关系图
     */
    Graph getAuthorGraphPartner(Integer id,Integer limit);

    /**
     * 获取指定作者同机构作者关系图
     * @param id 作者id
     * @param limit 上限设置
     * @return 作者同机构关系图
     */
    @GetMapping("/graph/affiliation")
    public Graph getAuthorGraphAffiliation(Integer id, Integer limit);

}
