package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.User;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface UserMapper {
    /**
     * 通过id删除用户
     * @author wph
     * @param id 用户id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 新增用户
     * @author wph
     * @param record 用户实体记录
     * @return
     */
    int insert(User record);

    /**
     * 检查属性是否有空值的新增用户
     * @author wph
     * @param record
     * @return
     */
    int insertSelective(User record);

    /**
     * 通过主键查找用户
     * @author wph
     * @param id 用户id
     * @return 用户实体
     */
    User selectByPrimaryKey(Integer id);

    /**
     * 通过用户名查找用户
     * @author wph
     * @param username 用户名
     * @return 用户实体
     */
    User selectByUsername(String username);

    /**
     * 检查是否有空值的 通过一个用户实体更新用户信息
     * @author wph
     * @param record 用户实体
     * @return
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 通过一个用户实体更新用户信息
     * @author wph
     * @param record 用户实体
     * @return
     */
    int updateByPrimaryKey(User record);
}