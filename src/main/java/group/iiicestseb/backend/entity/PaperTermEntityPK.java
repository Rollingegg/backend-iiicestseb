package group.iiicestseb.backend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperTermEntityPK implements Serializable {
    private int paperId;
    private int termId;

    @Column(name = "paper_id", nullable = false)
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Column(name = "term_id", nullable = false)
    @Id
    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTermEntityPK that = (PaperTermEntityPK) o;
        return paperId == that.paperId &&
                termId == that.termId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }
}
