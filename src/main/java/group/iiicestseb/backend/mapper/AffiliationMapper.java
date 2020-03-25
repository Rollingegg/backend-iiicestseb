package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import group.iiicestseb.backend.entity.Affiliation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

/**
 * @author wph
 * @date 2020/2/29
 */
@Mapper
public interface AffiliationMapper extends BaseMapper<Affiliation> {

    /**
     * 通过机构名查找机构
     *
     * @param name 机构名
     * @return 机构
     */
    @Select("select * from affiliation where name = #{name}")
    @ResultType(Affiliation.class)
    Affiliation selectByName(String name);
//    /**
//     * 通过id删除机构
//     *
//     * @param id 机构id
//     * @return 修改行数
//     */
//    @Delete("delete from affiliation where id = #{id,jdbcType=INTEGER}")
//    int deleteByPrimaryKey(Integer id);
//
//
//    /**
//     * 新增机构
//     *
//     * @param record 机构实体
//     * @return 新增机构id
//     */
//    @Insert("insert into affiliation (name) values ( #{name,jdbcType=VARCHAR});")
//    @Options(useGeneratedKeys = true,keyProperty = "id")
//    int insert(Affiliation record);
//
//    /**
//     * 新增机构列表
//     *
//     * @param affiliationList 机构实体列表
//     * @return 修改行数
//     */
//    int insertAffiliationList(@Param("affiliationList") List<Affiliation> affiliationList);
//
//    /**
//     * 通过id搜索机构
//     *
//     * @param id 机构id
//     * @return 机构实体
//     */
//    @Select("select * from affiliation where id = #{id,jdbcType=INTEGER}")
//    @ResultMap("AffiliationResultMap")
//    Affiliation selectByPrimaryKey(@Param("id") Integer id);
//
//    /**
//     * 更新机构信息
//     *
//     * @param record 机构实体
//     * @return 更新行数
//     */
//    @Update("update affiliation set name = #{name,jdbcType=VARCHAR} where id = #{id,jdbcType=INTEGER}")
//    int updateByPrimaryKey(Affiliation record);
//
//    /**
//     * 通过机构名选择机构
//     *
//     * @param name 机构名
//     * @return 机构实体
//     */
//    @Select("select * from affiliation where name=#{name}")
//    @ResultMap("AffiliationResultMap")
//    Affiliation selectByName(String name);
}