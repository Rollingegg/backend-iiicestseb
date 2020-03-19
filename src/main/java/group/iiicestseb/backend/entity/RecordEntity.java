package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "record", schema = "iiicestseb", catalog = "")
public class RecordEntity {
    private int id;
    private String searchRecord;
    private UserEntity userByUserId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "search_record", nullable = true, length = -1)
    public String getSearchRecord() {
        return searchRecord;
    }

    public void setSearchRecord(String searchRecord) {
        this.searchRecord = searchRecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RecordEntity that = (RecordEntity) o;
        return id == that.id &&
                Objects.equals(searchRecord, that.searchRecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchRecord);
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    public UserEntity getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(UserEntity userByUserId) {
        this.userByUserId = userByUserId;
    }
}
