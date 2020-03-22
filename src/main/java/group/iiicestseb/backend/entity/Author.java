package group.iiicestseb.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "author", schema = "iiicestseb")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC",strategy = GenerationType.IDENTITY)
    private int id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "fisrt_name")
    private String fisrtName;

    @Basic
    @Column(name = "last_name")
    private String lastName;

    @Basic
    @Column(name = "affiliation_id")
    private Integer affiliationId;

//    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
//    @JoinColumn(name = "affiliation_id", referencedColumnName = "id")
//    private Affiliation affiliationByAffiliationId;


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
        return Objects.hash(id, name, fisrtName, lastName/*, affiliationId*/);
    }

}
