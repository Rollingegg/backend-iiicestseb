package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.*;

import java.util.Collection;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */

public interface ManageService {


    /**
     * 通过文献id删除文献
     *
     * @param id 文献id
     */
    void deletePaperById(int id);

    /**
     * 插入文献
     *
     * @param paper 文献
     */
    void insertPaper(Paper paper);

    /**
     * 插入文献、关键词列表
     *
     * @param paperTerms 文献、关键词列表
     */
    void insertPaperTermList(List<PaperTerm> paperTerms);

    /**
     * 插入文献引用列表
     *
     * @param references 引用列表
     */
    void insertReferences(List<Reference> references);

    /**
     * 插入文献作者列表
     *
     * @param paperAuthors 文献作者列表
     */
    void insertPaperAuthorList(List<PaperAuthors> paperAuthors);

    /**
     * 根据IEEE的文献id查找文献
     *
     * @param articleId IEEE的文献id
     * @return 文献PO
     */
    Paper findPaperByArticleId(Integer articleId);

    /**
     * 根据术语名查找术语
     *
     * @param name 术语名
     * @return 术语PO
     */
    Term findTermByName(String name);

    /**
     * 批量存储术语
     *
     * @param termList 术语列表
     */
    void saveTermList(List<Term> termList);

    /**
     * 启动计算新论文的评分
     *
     * @return 计算的行数
     */
    Integer reComputePapersScore();

    /**
     * 获取论文的评分信息
     *
     * @param paperId 论文id
     * @return 论文评分
     */
    PaperStatistics findPaperStatistics(Integer paperId);

    /**
     * 更新指定论文的评分
     *
     * @param id 论文id
     * @return
     */
    PaperStatistics updatePaperScore(Integer id);

    /**
     * 批量更新论文的评分
     *
     * @param ids 论文id集合
     * @return 更新结果
     */
    Collection<PaperStatistics> updatePaperScoreBatch(Collection<Integer> ids);


    /**
     * 重算所有作者的统计信息
     *
     * @return 更新行数
     */
    Integer reComputeAuthorStatistics();

}
