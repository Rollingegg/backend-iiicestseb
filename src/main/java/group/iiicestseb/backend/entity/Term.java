package group.iiicestseb.backend.entity;


import lombok.Data;


/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Term {

    /**
     * 术语id
     */
    private Integer id;

    /**
     * 术语标准来源id
     */
    private Integer standardId;

    /**
     * 术语
     */
    private String word;


    public Term(Integer id, Integer standardId, String word) {
        this.id = id;
        this.standardId = standardId;
        this.word = word;
    }

    public Term() {
        super();
    }

}