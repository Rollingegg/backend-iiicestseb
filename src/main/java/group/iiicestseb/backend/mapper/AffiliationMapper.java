package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;

import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wph
 * @date 2020/2/29
 */
public interface AffiliationMapper {

    /**
     * 通过id删除机构
     *
     * @param id 机构id
     */
    @Delete("delete from affiliation where id = #{id,jdbcType=INTEGER}")
    void deleteByPrimaryKey(Integer id);


    /**
     * 新增机构
     *
     * @param record 机构实体
     */
    @Insert("insert into affiliation (id, name) values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true)
    void insert(Affiliation record);

    /**
     * 新增机构列表
     *
     * @param affiliationList 机构实体列表
     */
    void insertAffiliationList(@Param("affiliationList") List<Affiliation> affiliationList);

    /**
     * 通过id搜索机构
     *
     * @param id 机构id
     * @return 机构实体
     */
    @Select("select * from affiliation where id = #{id,jdbcType=INTEGER}")
    @ResultMap("AffiliationResultMap")
    Affiliation selectByPrimaryKey(@Param("id") Integer id);

    /**
     * 更新机构信息
     *
     * @param record 机构实体
     */
    @Update("update affiliation set name = #{name,jdbcType=VARCHAR} where id = #{id,jdbcType=INTEGER}")
    void updateByPrimaryKey(Affiliation record);

    /**
     * 通过机构名选择机构
     *
     * @param name 机构名
     * @return 机构实体
     */
    @Select("select * from affiliation where name=#{name}")
    @ResultMap("AffiliationResultMap")
    Affiliation selectByName(String name);
}