package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "iiicestseb", catalog = "")
public class UserEntity {
    private int id;
    private String username;
    private String password;
    private String privilegeLevel;
    private Collection<RecordEntity> recordsById;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "username", nullable = true, length = 32)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 32)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "privilege_level", nullable = true, length = 20)
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
        UserEntity that = (UserEntity) o;
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
    public Collection<RecordEntity> getRecordsById() {
        return recordsById;
    }

    public void setRecordsById(Collection<RecordEntity> recordsById) {
        this.recordsById = recordsById;
    }
}
