package group.iiicestseb.backend.vo.paper;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import group.iiicestseb.backend.entity.Paper;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author jh
 * @date 2020/3/29
 */
@Data
public class PaperOverview {

    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    @TableField("title")
    private String title;

    @TableField("paper_abstract")
    private String paperAbstract;

    @TableField("author_keywords")
    private String authorKeywords;

    @TableField("chron_date")
    private LocalDateTime chronDate;

    @TableField("article_id")
    private Integer articleId;


}
