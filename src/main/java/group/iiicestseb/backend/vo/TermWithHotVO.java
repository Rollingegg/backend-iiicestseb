package group.iiicestseb.backend.vo;

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
    private String word;

    /**
     * 热度
     */
    private Integer hot;

    public TermWithHotVO() {
        super();
    }

    public TermWithHotVO(String word) {
        this.word = word;
    }

    public TermWithHotVO(String word, Integer hot) {
        this.word = word;
        this.hot = hot;
    }

    public TermWithHotVO(Integer id, String word, Integer hot) {
        this.id = id;
        this.word = word;
        this.hot = hot;
    }

}
