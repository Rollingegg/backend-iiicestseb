package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;

/**
 * @author wph
 * @date 2020/2/29
 */
public interface AffiliationMapper {

    /**
     * 通过id删除机构
     * @param id 机构id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增机构
     * @param record 机构实体
     * @return
     */
    int insert(Affiliation record);

    /**
     * 检测有没有空值的 新增机构
     * @param record 机构实体
     * @return
     */
    int insertSelective(Affiliation record);

    /**
     * 通过id搜索机构
     * @param id 机构id
     * @return 机构实体
     */
    Affiliation selectByPrimaryKey(Integer id);

    /**
     * 检测空值的 更新机构信息
     * @param record 机构实体
     * @return
     */
    int updateByPrimaryKeySelective(Affiliation record);

    /**
     * 更新机构信息
     * @param record 机构实体
     * @return
     */
    int updateByPrimaryKey(Affiliation record);

    /**
     * 通过机构名选择机构
     * @param name 机构名
     * @return 机构实体
     */
    Affiliation selectByName(String name);
}