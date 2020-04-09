package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Objects;

/**
 * @author jh
 * @date 2020/3/25
 */
@TableName(value = "term")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Term {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("name")
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Term that = (Term) o;
        return id == that.id &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }


}
