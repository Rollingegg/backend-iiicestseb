package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@TableName("reference")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Reference {

    @TableId("id")
    private int id;

    @TableField("reference_order")
    private Integer referenceOrder;

    @TableField("text")
    private String text;

    @TableField("title")
    private String title;

    @TableField("google_scholar_link")
    private String googleScholarLink;

    @TableField("ref_type")
    private String refType;

    @TableField("article_id")
    private Integer articleId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reference that = (Reference) o;
        return id == that.id &&
                Objects.equals(referenceOrder, that.referenceOrder) &&
                Objects.equals(text, that.text) &&
                Objects.equals(title, that.title) &&
                Objects.equals(googleScholarLink, that.googleScholarLink) &&
                Objects.equals(refType, that.refType) &&
                Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, referenceOrder, text, title, googleScholarLink, refType, articleId);
    }
}
