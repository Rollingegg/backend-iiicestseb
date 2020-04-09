package group.iiicestseb.backend.exception.user;

import lombok.Getter;
import lombok.Setter;

/**
 * 用户登录信息错误异常
 * @author wph
 * @date 2020/2/22
 */
@Getter
@Setter
public class WrongLoginInfoException extends RuntimeException{
    public static final String MSG = "用户名或密码错误";
    /**
     * 自定义异常码
     */
    private Integer code;

    public WrongLoginInfoException() {
        super(MSG);
        this.code = 1002;
    }
}