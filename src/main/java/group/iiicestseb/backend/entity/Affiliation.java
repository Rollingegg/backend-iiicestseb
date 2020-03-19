package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "affiliation", schema = "iiicestseb")
public class Affiliation {
    private int id;
    private String name;
    private Collection<Author> authorsById;

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
        Affiliation that = (Affiliation) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "affiliationByAffiliationId")
    public Collection<Author> getAuthorsById() {
        return authorsById;
    }

    public void setAuthorsById(Collection<Author> authorsById) {
        this.authorsById = authorsById;
    }
}
