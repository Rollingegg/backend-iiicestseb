package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


/**
 * @author jh
 * @date 2020/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("author")
public class Author {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("name")
    private String name;

    @TableField("first_name")
    private String firstName;

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
                Objects.equals(firstName, that.firstName) &&
                Objects.equals(lastName, that.lastName) &&
                Objects.equals(affiliationId, that.affiliationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, firstName, lastName, affiliationId);
    }

}
