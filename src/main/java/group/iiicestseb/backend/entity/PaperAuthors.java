package group.iiicestseb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_authors", schema = "iiicestseb")
@IdClass(PaperAuthorsPK.class)
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaperAuthors {
    @Id
    @Column(name = "author_id")
    private int authorId;

    @Id
    @Column(name = "paper_id")
    private int paperId;

    @Basic
    @Column(name = "author_order")
    private Integer authorOrder;

//    @ManyToOne
//    @JoinColumn(name = "paper_id", referencedColumnName = "id", nullable = false)
//    private Paper paperByPaperId;
//
//    @ManyToOne
//    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
//    private Author authorByAuthorId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperAuthors that = (PaperAuthors) o;
        return authorId == that.authorId &&
                paperId == that.paperId &&
                Objects.equals(authorOrder, that.authorOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, paperId, authorOrder);
    }
}
