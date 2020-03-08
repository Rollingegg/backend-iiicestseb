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
     * 术语
     */
    private String word;

    public Term() {
        super();
    }

    public Term(String word) {
        this.word = word;
    }

    public Term(Integer id, String word) {
        this.id = id;
        this.word = word;
    }

}