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
@TableName("affiliation")
public class Affiliation {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    @TableField("name")
    private String name;

//    @OneToMany(mappedBy = "affiliationByAffiliationId")
//    private Collection<Author> authorsById;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Affiliation that = (Affiliation) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}
