package group.iiicestseb.backend.exception.user;

import lombok.Data;


/**
 * 用户注册账号已存在异常
 * @author wph
 * @date 2020/2/22
 */
@Data
public class UserAlreadyRegisterException extends RuntimeException{

    /**
     * 自定义异常码
     */
    private Integer code;

    public UserAlreadyRegisterException() {
        super("用户已注册");
        this.code = 1;
    }
}
