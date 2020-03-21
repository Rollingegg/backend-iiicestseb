package group.iiicestseb.backend.exception.paper;

public class PaperFormException extends RuntimeException{
    /**
     * 自定义异常码
     */
    private Integer code;

    public PaperFormException() {
        super("请输入搜索内容");
        this.code = 2002;
    }
}
