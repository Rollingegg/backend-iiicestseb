package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "iiicestseb", catalog = "")
public class Author {
    private int id;
    private String name;
    private String fisrtName;
    private String lastName;
    private Integer affiliationId;
    private Affiliation affiliationByAffiliationId;

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

    @Basic
    @Column(name = "fisrt_name")
    public String getFisrtName() {
        return fisrtName;
    }

    public void setFisrtName(String fisrtName) {
        this.fisrtName = fisrtName;
    }

    @Basic
    @Column(name = "last_name")
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Basic
    @Column(name = "affiliation_id")
    public Integer getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author that = (Author) o;
        return id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(fisrtName, that.fisrtName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(affiliationId, that.affiliationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, fisrtName, lastName, affiliationId);
    }

    @ManyToOne
    @JoinColumn(name = "affiliation_id", referencedColumnName = "id")
    public Affiliation getAffiliationByAffiliationId() {
        return affiliationByAffiliationId;
    }

    public void setAffiliationByAffiliationId(Affiliation affiliationByAffiliationId) {
        this.affiliationByAffiliationId = affiliationByAffiliationId;
    }
}
