package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;



@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("author")
public class Author {

    @TableId("id")
    private int id;

    @TableField("name")
    private String name;

    @TableField("firstname")
    private String fisrtName;

    @TableField("last_name")
    private String lastName;

    @TableField("affiliation_id")
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
