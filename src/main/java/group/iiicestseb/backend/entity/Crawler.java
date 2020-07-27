package group.iiicestseb.backend.entity;

/**
 * @author jh
 * @date 2020/7/14
 */

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import group.iiicestseb.backend.form.CrawlerForm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@TableName(value = "crawler")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Crawler {

    @TableId(value = "crawler_id", type = IdType.AUTO)
    private Integer crawlerId;

    @TableField("add_time")
    private LocalDateTime addTime;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("state")
    private String state;

    @TableField("conference_name")
    private String conferenceName;

    @TableField("start_year")
    private Integer startYear;

    @TableField("end_year")
    private Integer endYear;

    @TableField("total_count")
    private Integer totalCount;

    @TableField("success_count")
    private Integer successCount;

    @TableField("existed_count")
    private Integer existedCount;

    @TableField("error_count")
    private Integer errorCount;

    /**
     * 爬虫任务的几种状态
     */
    public enum STATE {
        //
        Waiting("等待中"),
        Running("运行中"),
        Finished("已完成"),
        Canceled("已取消"),
        Fail("失败");
        public final String value;

        STATE(String value) {
            this.value = value;
        }

    }

    public Crawler(CrawlerForm form) {
        this.addTime = LocalDateTime.now();
        this.state = STATE.Waiting.value;
        this.conferenceName = form.getConferenceName();
        this.startYear = form.getStartYear();
        this.endYear = form.getEndYear();
    }

}
