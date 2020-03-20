package group.iiicestseb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaperTermPK implements Serializable {
    @Column(name = "paper_id")
    @Id
    private int paperId;

    @Column(name = "term_id")
    @Id
    private int termId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperTermPK that = (PaperTermPK) o;
        return paperId == that.paperId &&
                termId == that.termId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, termId);
    }
}
