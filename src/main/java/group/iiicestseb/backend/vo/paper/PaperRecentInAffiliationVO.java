package group.iiicestseb.backend.vo.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperRecentInAffiliationVO {

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
