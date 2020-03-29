package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.Author;

import java.util.Collection;
import java.util.List;

/**
 * @author wph
 * @date 2020/03/01
 */
public interface AuthorService {

    /**
     * 根据作者名查询作者
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
//    /**
//     * 作者页面所需要的作者详细信息
//     * @author wph
//     * @param name 作者名
//     * @return 作者详细信息VO
//     */
//    AuthorInfoVO getAuthorInfo(String name);
//
//    /**
//     * 查询论文所有作者
//     * @param id 论文id
//     * @return 作者名称列表
//     */
//    CopyOnWriteArrayList<String> getAuthorByPaperId(int id);
}
