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
public class PaperAuthorsPK implements Serializable {

    @Column(name = "author_id")
    @Id
    private int authorId;

    @Column(name = "paper_id")
    @Id
    private int paperId;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperAuthorsPK that = (PaperAuthorsPK) o;
        return authorId == that.authorId &&
                paperId == that.paperId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, paperId);
    }
}
