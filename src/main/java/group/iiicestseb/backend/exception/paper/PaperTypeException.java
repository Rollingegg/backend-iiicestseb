package group.iiicestseb.backend.exception.paper;

/**
 * @author wph
 */
public class PaperTypeException extends RuntimeException{
    /**
     * 自定义异常码
     */
    private Integer code;

    public static final String MESSAGE = "type类型错误,前端检查type类型的传递参数";

    public PaperTypeException() {
        super(MESSAGE);
        this.code = 2003;
    }
}
