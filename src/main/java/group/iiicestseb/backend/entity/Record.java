package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@TableName("record")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Record {

    @TableId(value = "id",type = IdType.AUTO)
    private int id;

    @TableField("search_record")
    private String searchRecord;

    @TableField("user_id")
    private Integer userId;

//    @ManyToOne
//    @JoinColumn(name = "user_id", referencedColumnName = "id")
//    private User userByUserId;


    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Record that = (Record) o;
        return id == that.id &&
                Objects.equals(searchRecord, that.searchRecord) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, searchRecord, userId);
    }

}
