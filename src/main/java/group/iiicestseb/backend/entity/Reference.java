package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reference", schema = "iiicestseb")
public class Reference {
    private int id;
    private Integer referenceOrder;
    private String text;
    private String title;
    private String googleScholarLink;
    private String refType;
    private Integer articleId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reference_order")
    public Integer getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(Integer referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    @Basic
    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "google_scholar_link")
    public String getGoogleScholarLink() {
        return googleScholarLink;
    }

    public void setGoogleScholarLink(String googleScholarLink) {
        this.googleScholarLink = googleScholarLink;
    }

    @Basic
    @Column(name = "ref_type")
    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    @Basic
    @Column(name = "article_id")
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
