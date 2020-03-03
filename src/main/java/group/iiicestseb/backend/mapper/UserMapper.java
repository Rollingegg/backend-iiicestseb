package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.User;
import org.apache.ibatis.annotations.*;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface UserMapper {
    /**
     * 通过id删除用户
     * @author wph
     * @param id 用户id
     * @return 影响行数
     */
    @Delete("delete from user where id = #{id,jdbcType=INTEGER}")
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增用户
     * @author wph
     * @param record 用户实体记录
     * @return 插入的主键
     */
    @Insert("insert into user (record_id, username, password, privilege_level) " +
            "values (" +
            "#{recordId,jdbcType=INTEGER}, " +
            "#{username,jdbcType=VARCHAR},  " +
            "#{password,jdbcType=VARCHAR}, " +
            "#{privilegeLevel,jdbcType=VARCHAR});" +
            "select LAST_INSERT_ID() as id")
    @Options(useGeneratedKeys = true)
    int insert(User record);

    /**
     * 通过主键查找用户
     * @author wph
     * @param id 用户id
     * @return 用户实体
     */
    @Select("select * from user where id = #{id,jdbcType=INTEGER}")
    User selectByPrimaryKey(Integer id);

    /**
     * 通过用户名查找用户
     * @author wph
     * @param username 用户名
     * @return 用户实体
     */
    @Select("select * from user where username = #{username,jdbcType = VARCHAR}")
    User selectByUsername(String username);


    /**
     * 通过一个用户实体更新用户信息
     * @author wph
     * @param record 用户实体
     * @return 影响行数
     */
    @Update("    update user" +
            "    set record_id = #{recordId,jdbcType=INTEGER}," +
            "      username = #{username,jdbcType=VARCHAR}," +
            "      password = #{password,jdbcType=VARCHAR}," +
            "      privilege_level = #{privilegeLevel,jdbcType=VARCHAR}" +
            "    where id = #{id,jdbcType=INTEGER}")
    int updateByPrimaryKey(User record);
}