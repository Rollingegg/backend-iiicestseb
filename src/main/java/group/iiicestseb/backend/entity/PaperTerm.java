package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_term", schema = "iiicestseb", catalog = "")
@IdClass(PaperTermPK.class)
public class PaperTerm {
    private int paperId;
    private int termId;
    private Paper paperByPaperId;
    private Term termByTermId;

    @Id
    @Column(name = "paper_id")
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "term_id")
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
        PaperTerm that = (PaperTerm) o;
        return paperId == that.paperId &&
                termId == that.termId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "id", nullable = false)
    public Paper getPaperByPaperId() {
        return paperByPaperId;
    }

    public void setPaperByPaperId(Paper paperByPaperId) {
        this.paperByPaperId = paperByPaperId;
    }

    @ManyToOne
    @JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false)
    public Term getTermByTermId() {
        return termByTermId;
    }

    public void setTermByTermId(Term termByTermId) {
        this.termByTermId = termByTermId;
    }
}
