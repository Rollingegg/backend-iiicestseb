package group.iiicestseb.backend.vo.statistics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperCountPerYearVO {

    /**
     * 年份
     */
    Date year;

    /**
     * 发表数量
     */
    Integer count;
}
