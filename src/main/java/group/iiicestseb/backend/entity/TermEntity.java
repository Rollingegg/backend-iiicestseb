package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "term", schema = "iiicestseb", catalog = "")
public class TermEntity {
    private int id;
    private String name;
    private Collection<PaperTermEntity> paperTermsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TermEntity that = (TermEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "termByTermId")
    public Collection<PaperTermEntity> getPaperTermsById() {
        return paperTermsById;
    }

    public void setPaperTermsById(Collection<PaperTermEntity> paperTermsById) {
        this.paperTermsById = paperTermsById;
    }
}
