package group.iiicestseb.backend.vo.user;

import group.iiicestseb.backend.entity.Record;
import group.iiicestseb.backend.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 用户信息vo
 * @author wph
 * @date 2020/03/01
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVO {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户浏览记录
     */
    private List<Record> recordList;

    /**
     * 权限等级
     */
    private String privilegeLevel;

    public UserVO(User user, List<Record> recordList) {
        this.id = user.getId();
        this.recordList = recordList;
        this.username = user.getUsername();
        this.privilegeLevel = user.getPrivilegeLevel();
    }
}
