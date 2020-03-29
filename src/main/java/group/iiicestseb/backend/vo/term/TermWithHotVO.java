package group.iiicestseb.backend.vo.term;

import lombok.Data;

/**
 * @author jh
 * @date 2020/3/4
 */
@Data
public class TermWithHotVO {

    /**
     * 术语id
     */
    private Integer id;

    /**
     * 术语
     */
    private String name;

    /**
     * 热度
     */
    private Integer hot;

    public TermWithHotVO() {
        super();
    }

    public TermWithHotVO(String name) {
        this.name = name;
    }

    public TermWithHotVO(String name, Integer hot) {
        this.name = name;
        this.hot = hot;
    }

    public TermWithHotVO(Integer id, String name, Integer hot) {
        this.id = id;
        this.name = name;
        this.hot = hot;
    }

}
