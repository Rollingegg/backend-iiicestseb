package group.iiicestseb.backend.entity;

import lombok.Data;
/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class PaperTerm {

    /**
     * 文献术语_关系id
     */
    private Integer id;

    /**
     * 文献id
     */
    private Integer paperId;

    /**
     * 术语id
     */
    private Integer termId;

    public PaperTerm() {
        super();
    }


    public PaperTerm(Integer id, Integer paperId, Integer termId) {
        this.id = id;
        this.paperId = paperId;
        this.termId = termId;
    }

    public PaperTerm(Paper paper, Term term) {
        this.paperId = paper.getId();
        this.termId = term.getId();
    }

}