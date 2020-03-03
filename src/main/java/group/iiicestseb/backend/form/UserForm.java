package group.iiicestseb.backend.form;

import lombok.Data;

/**
 * 用户登录信息表单
 * @author wph
 * @date 2020/03/01
 */
@Data
public class UserForm {

    /**
     * 用户登录用户名
     */
    private String username;

    /**
     * 用户登录密码
     */
    private String password;

}
