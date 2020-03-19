package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "iiicestseb", catalog = "")
public class User {
    private int id;
    private String username;
    private String password;
    private String privilegeLevel;
    private Collection<Record> recordsById;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "privilege_level")
    public String getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(String privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return id == that.id &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password) &&
                Objects.equals(privilegeLevel, that.privilegeLevel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, privilegeLevel);
    }

    @OneToMany(mappedBy = "userByUserId")
    public Collection<Record> getRecordsById() {
        return recordsById;
    }

    public void setRecordsById(Collection<Record> recordsById) {
        this.recordsById = recordsById;
    }
}
