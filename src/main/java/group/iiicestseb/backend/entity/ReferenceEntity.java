package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "reference", schema = "iiicestseb", catalog = "")
public class ReferenceEntity {
    private int id;
    private Integer referenceOrder;
    private String text;
    private String title;
    private String googleScholarLink;
    private String refType;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "reference_order", nullable = true)
    public Integer getReferenceOrder() {
        return referenceOrder;
    }

    public void setReferenceOrder(Integer referenceOrder) {
        this.referenceOrder = referenceOrder;
    }

    @Basic
    @Column(name = "text", nullable = true, length = -1)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "google_scholar_link", nullable = true, length = -1)
    public String getGoogleScholarLink() {
        return googleScholarLink;
    }

    public void setGoogleScholarLink(String googleScholarLink) {
        this.googleScholarLink = googleScholarLink;
    }

    @Basic
    @Column(name = "ref_type", nullable = true, length = 20)
    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReferenceEntity that = (ReferenceEntity) o;
        return id == that.id &&
                Objects.equals(referenceOrder, that.referenceOrder) &&
                Objects.equals(text, that.text) &&
                Objects.equals(title, that.title) &&
                Objects.equals(googleScholarLink, that.googleScholarLink) &&
                Objects.equals(refType, that.refType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceOrder, text, title, googleScholarLink, refType);
    }
}
