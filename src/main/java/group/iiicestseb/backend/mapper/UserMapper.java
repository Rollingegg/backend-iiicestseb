package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface UserMapper extends JpaRepository<User,Integer> {

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户实体
     */
    public User findByUsername(String username);
}