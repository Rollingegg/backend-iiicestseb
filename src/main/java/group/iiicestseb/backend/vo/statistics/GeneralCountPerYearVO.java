package group.iiicestseb.backend.vo.statistics;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    String year;

    /**
     * 发表数量
     */
    Integer count;

    public void setYear(String year) {
        this.year = year.substring(0,year.indexOf("-"));
    }
}
