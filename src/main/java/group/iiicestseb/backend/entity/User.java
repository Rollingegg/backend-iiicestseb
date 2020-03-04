package group.iiicestseb.backend.entity;


import lombok.Data;
/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class User {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 用户浏览记录id
     */
    private Integer recordId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 权限等级
     */
    private String privilegeLevel;

    public User(Integer id, Integer recordId, String username, String password, String privilegeLevel) {
        this.id = id;
        this.recordId = recordId;
        this.username = username;
        this.password = password;
        this.privilegeLevel = privilegeLevel;
    }

    public User() {
        super();
    }
}