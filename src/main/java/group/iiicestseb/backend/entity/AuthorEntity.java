package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "iiicestseb", catalog = "")
public class AuthorEntity {
    private int id;
    private String name;
    private String fisrtName;
    private String lastName;
    private AffiliationEntity affiliationByAffiliationId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 40)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "fisrt_name", nullable = true, length = 20)
    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    @Basic
    @Column(name = "last_name", nullable = true, length = 20)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorEntity that = (AuthorEntity) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(fisrtName, that.fisrtName) &&
                Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fisrtName, lastName);
    }

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "id")
    public AffiliationEntity getAffiliationByAffiliationId() {
        return affiliationByAffiliationId;
    }

    public void setAffiliationByAffiliationId(AffiliationEntity affiliationByAffiliationId) {
        this.affiliationByAffiliationId = affiliationByAffiliationId;
    }
}
