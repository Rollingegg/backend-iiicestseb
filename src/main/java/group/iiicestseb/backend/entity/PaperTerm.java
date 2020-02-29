package group.iiicestseb.backend.entity;

public class PaperTerm {
    private Integer id;

    private Integer paperId;

    private Integer termId;

    public PaperTerm(Integer id, Integer paperId, Integer termId) {
        this.id = id;
        this.paperId = paperId;
        this.termId = termId;
    }

    public PaperTerm() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }

    public Integer getTermId() {
        return termId;
    }

    public void setTermId(Integer termId) {
        this.termId = termId;
    }
}