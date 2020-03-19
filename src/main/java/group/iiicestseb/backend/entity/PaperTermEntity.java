package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_term", schema = "iiicestseb", catalog = "")
@IdClass(PaperTermEntityPK.class)
public class PaperTermEntity {
    private int paperId;
    private int termId;
    private PaperEntity paperByPaperId;
    private TermEntity termByTermId;

    @Id
    @Column(name = "paper_id", nullable = false)
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Id
    @Column(name = "term_id", nullable = false)
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
        PaperTermEntity that = (PaperTermEntity) o;
        return paperId == that.paperId &&
                termId == that.termId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }

    @ManyToOne
    @JoinColumn(name = "paper_id", referencedColumnName = "id", nullable = false)
    public PaperEntity getPaperByPaperId() {
        return paperByPaperId;
    }

    public void setPaperByPaperId(PaperEntity paperByPaperId) {
        this.paperByPaperId = paperByPaperId;
    }

    @ManyToOne
    @JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false)
    public TermEntity getTermByTermId() {
        return termByTermId;
    }

    public void setTermByTermId(TermEntity termByTermId) {
        this.termByTermId = termByTermId;
    }
}
