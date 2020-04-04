package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * @author jh
 * @date 2020/4/3
 */
@TableName("paper_statistics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorStatistics {

    @TableField("author_id")
    private Integer authorId;

    @TableField("h_index")
    private Integer hIndex;

    @TableField("g_index")
    private Integer gIndex;

    @TableField("avg_cite")
    private Double avgCite;

    @TableField("paper_num")
    private Integer paperNum;

    @TableField("ase_paper_num")
    private Integer asePaperNum;

    @TableField("icse_paper_num")
    private Integer icsePaperNum;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorStatistics that = (AuthorStatistics) o;
        return authorId.equals(that.authorId) &&
                hIndex.equals(that.hIndex) &&
                gIndex.equals(that.gIndex) &&
                avgCite.equals(that.avgCite) &&
                paperNum.equals(that.paperNum) &&
                asePaperNum.equals(that.asePaperNum) &&
                icsePaperNum.equals(that.icsePaperNum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, hIndex, gIndex, avgCite, paperNum, asePaperNum, icsePaperNum);
    }

}
