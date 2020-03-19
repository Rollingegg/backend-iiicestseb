package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paper_authors", schema = "iiicestseb", catalog = "")
@IdClass(PaperAuthorsEntityPK.class)
public class PaperAuthorsEntity {
    private int authorId;
    private int paperId;
    private Integer authorOrder;

    @Id
    @Column(name = "author_id", nullable = false)
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Id
    @Column(name = "paper_id", nullable = false)
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Basic
    @Column(name = "author_order", nullable = true)
    public Integer getAuthorOrder() {
        return authorOrder;
    }

    public void setAuthorOrder(Integer authorOrder) {
        this.authorOrder = authorOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperAuthorsEntity that = (PaperAuthorsEntity) o;
        return authorId == that.authorId &&
                paperId == that.paperId &&
                Objects.equals(authorOrder, that.authorOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, paperId, authorOrder);
    }
}
