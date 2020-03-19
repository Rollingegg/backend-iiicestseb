package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "conference", schema = "iiicestseb", catalog = "")
public class Conference {
    private int id;
    private String name;
    private Collection<Paper> papersById;

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
        Conference that = (Conference) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @OneToMany(mappedBy = "conferenceByConferenceId")
    public Collection<Paper> getPapersById() {
        return papersById;
    }

    public void setPapersById(Collection<Paper> papersById) {
        this.papersById = papersById;
    }
}
