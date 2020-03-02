package group.iiicestseb.backend.exception.user;


import lombok.Data;

/**
 * 用户登录信息错误异常
 * @author wph
 * @date 2020/2/22
 */
@Data
public class WrongLoginInfoException extends RuntimeException{

    /**
     * 自定义异常码
     */
    private Integer code;

    public WrongLoginInfoException() {
        super("用户名或密码错误");
        this.code = 2;
    }
}