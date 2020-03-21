package group.iiicestseb.backend.exception.paper;

/**
 * 临时异常类 重构后不该在
 */
public class NoPaperFoundException extends RuntimeException{

    /**
     * 自定义异常码
     */
    private Integer code;
    public NoPaperFoundException() {
        super("无相关结果");
        this.code = 2001;
    }
}