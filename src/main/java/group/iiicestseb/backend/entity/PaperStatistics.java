package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author jh
 * @date 2020/4/1
 */
@Data
@TableName("paper_statistics")
@AllArgsConstructor
@NoArgsConstructor
public class PaperStatistics {

    @TableField("paper_id")
    private Integer paperId;

    @TableField("score")
    private Double score;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperStatistics that = (PaperStatistics) o;
        return paperId.equals(that.paperId) &&
                score.equals(that.score);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paperId, score);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AuthorPaperCites {
        Integer authorId;
        Integer cite;
        String conference;
    }

}
