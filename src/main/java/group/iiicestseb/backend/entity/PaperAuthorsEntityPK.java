package group.iiicestseb.backend.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class PaperAuthorsEntityPK implements Serializable {
    private int authorId;
    private int paperId;

    @Column(name = "author_id", nullable = false)
    @Id
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Column(name = "paper_id", nullable = false)
    @Id
    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperAuthorsEntityPK that = (PaperAuthorsEntityPK) o;
        return authorId == that.authorId &&
                paperId == that.paperId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, paperId);
    }
}
