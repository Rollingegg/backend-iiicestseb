package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author jh
 * @date 2020/2/22
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体
     */
    //public User findByUsername(String username);
}