package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.*;

import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */

public interface PaperManageService {


    /**
     * 通过文献id删除文献
     * @param id 文献id
     */
    void deletePaperById(int id);

    /**
     * 插入文献
     * @param paper 文献
     */
    void insertPaper(Paper paper);

    /**
     * 插入文献、关键词列表
     * @param paperTerms 文献、关键词列表
     */
    void insertPaperTermList(List<PaperTerm> paperTerms);

    /**
     * 插入文献引用列表
     * @param references 引用列表
     */
    void insertReferences(List<Reference> references);

    /**
     * 插入文献作者列表
     * @param paperAuthors 文献作者列表
     */
    void insertPaperAuthorList(List<PaperAuthors> paperAuthors);

    /**
     * 根据IEEE的文献id查找文献
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
     * @param termList 术语列表
     */
    void saveTermList(List<Term> termList);


}
