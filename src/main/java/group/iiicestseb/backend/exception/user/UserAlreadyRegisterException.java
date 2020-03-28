package group.iiicestseb.backend.exception.user;


import lombok.Getter;
import lombok.Setter;

/**
 * 用户注册账号已存在异常
 * @author wph
 * @date 2020/2/22
 */
@Getter
@Setter
public class UserAlreadyRegisterException extends RuntimeException{
    public static final String MSG = "用户已注册";
    /**
     * 自定义异常码
     */
    private Integer code;

    public UserAlreadyRegisterException() {
        super(MSG);
        this.code = 1001;
    }
}
