package group.iiicestseb.backend.vo.statistics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 通用 年份-次数 统计vo
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GeneralCountPerYearVO {

    /**
     * 年份
     */
    Date year;

    /**
     * 发表数量
     */
    Integer count;
}
