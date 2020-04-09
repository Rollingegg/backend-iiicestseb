package group.iiicestseb.backend.vo.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 展现在作者、机构overview页面的简单论文信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperBasicVO {

    /**
     * 论文id
     */
    private Integer id;

    /**
     * 论文名称
     */
    private String title;

    /**
     * 出版日期
     */
    private LocalDateTime chronDate;
}
