package group.iiicestseb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_term", schema = "iiicestseb")
@IdClass(PaperTermPK.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaperTerm {
    @Id
    @Column(name = "paper_id")
    private int paperId;

    @Id
    @Column(name = "term_id")
    private int termId;

//    @ManyToOne
//    @JoinColumn(name = "paper_id", referencedColumnName = "id", nullable = false)
//    private Paper paperByPaperId;
//
//    @ManyToOne
//    @JoinColumn(name = "term_id", referencedColumnName = "id", nullable = false)
//    private Term termByTermId;


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

}
