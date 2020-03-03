package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Author;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author wph
 * @date 2020/2/29
 */
public interface AuthorMapper {

    /**
     * 通过id删除作者
     * @author wph
     * @param id 作者id
     */
    @Delete("delete from author where id = #{id,jdbcType=INTEGER}")
    void deleteByPrimaryKey(Integer id);

    /**
     * 增加作者
     * @param record 作者实体
     */
    @Insert("insert into author (id, name, affiliation_id) values (#{id,jdbcType=INTEGER}, #{name,jdbcType=VARCHAR}, #{affiliationId,jdbcType=INTEGER})")
    @Options(useGeneratedKeys = true)
    void insert(Author record);

    /**
     * 增加作者列表
     * @param authorList 作者实体列表
     * @return 插入的行
     */
    int insertAuthorList(@Param("authorList") List<Author> authorList);

    /**
     * 通过id搜寻作者
     * @param id 作者id
     * @return 作者实体
     */
    @Select("select * from author where id = #{id,jdbcType=INTEGER}")
    @ResultMap("AuthorResultMap")
    Author selectByPrimaryKey(Integer id);

    /**
     * 更新作者信息
     * @param record 作者信息实体
     */
    @Update("update author set name = #{name,jdbcType=VARCHAR}, affiliation_id = #{affiliationId,jdbcType=INTEGER} where id = #{id,jdbcType=INTEGER}")
    void updateByPrimaryKey(Author record);

    /**
     * 根据作者名字查找作者信息
     * @param name 作者名
     * @return 作者实体
     */
    @Select("select * from author where name = #{name,jdbcType=VARCHAR}")
    @ResultMap("AuthorResultMap")
    Author selectByName(String name);
}