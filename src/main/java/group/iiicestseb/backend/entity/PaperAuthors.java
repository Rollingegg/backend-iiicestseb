package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

import java.util.Objects;


/**
 * @author jh
 * @date 2020/3/25
 */
@TableName("paper_authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperAuthors {
    @TableField("author_id")
    private int authorId;

    @TableField("paper_id")
    private int paperId;

    @TableField("author_order")
    private Integer authorOrder;

//    @ManyToOne
//    @JoinColumn(name = "paper_id", referencedColumnName = "id", nullable = false)
//    private Paper paperByPaperId;
//
//    @ManyToOne
//    @JoinColumn(name = "author_id", referencedColumnName = "id", nullable = false)
//    private Author authorByAuthorId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperAuthors that = (PaperAuthors) o;
        return authorId == that.authorId &&
                paperId == that.paperId &&
                Objects.equals(authorOrder, that.authorOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, paperId, authorOrder);
    }
}
