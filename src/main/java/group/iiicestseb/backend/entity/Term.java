package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "term", schema = "iiicestseb")
public class Term {
    private int id;
    private String name;
    private Collection<PaperTerm> paperTermsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
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
        Term that = (Term) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "termByTermId")
    public Collection<PaperTerm> getPaperTermsById() {
        return paperTermsById;
    }

    public void setPaperTermsById(Collection<PaperTerm> paperTermsById) {
        this.paperTermsById = paperTermsById;
    }
}
