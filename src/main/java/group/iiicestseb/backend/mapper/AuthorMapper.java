package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Author;

/**
 * @author wph
 * @date 2020/2/29
 */
public interface AuthorMapper {

    /**
     * 通过id删除作者
     * @author wph
     * @param id 作者id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 增加作者
     * @param record 作者实体
     * @return
     */
    int insert(Author record);

    /**
     * 检测有没有空值的 增加作者
     * @param record 作者实体
     * @return
     */
    int insertSelective(Author record);

    /**
     * 通过id搜寻作者
     * @param id 作者id
     * @return
     */
    Author selectByPrimaryKey(Integer id);

    /**
     * 检测有没有空值的 更新作者信息
     * @param record 作者信息实体
     * @return
     */
    int updateByPrimaryKeySelective(Author record);

    /**
     * 更新作者信息
     * @param record 作者信息实体
     * @return
     */
    int updateByPrimaryKey(Author record);

    /**
     * 根据作者名字查找作者信息
     * @author wph
     * @param name
     * @return
     */
    Author selectByName(String name);
}