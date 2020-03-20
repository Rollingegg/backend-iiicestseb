package group.iiicestseb.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reference", schema = "iiicestseb")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "reference_order")
    private Integer referenceOrder;

    @Basic
    @Column(name = "text")
    private String text;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "google_scholar_link")
    private String googleScholarLink;

    @Basic
    @Column(name = "ref_type")
    private String refType;

    @Basic
    @Column(name = "article_id")
    private Integer articleId;




    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference that = (Reference) o;
        return id == that.id &&
                Objects.equals(referenceOrder, that.referenceOrder) &&
                Objects.equals(text, that.text) &&
                Objects.equals(title, that.title) &&
                Objects.equals(googleScholarLink, that.googleScholarLink) &&
                Objects.equals(refType, that.refType) &&
                Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceOrder, text, title, googleScholarLink, refType, articleId);
    }
}
