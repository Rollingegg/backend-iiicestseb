package group.iiicestseb.backend.vo.author;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorHotInAffiliationVO {

    /**
     * 作者id
     */
    private int id;

    /**
     * 作者名称
     */
    private String name;

    /**
     * h指数
     */
    private Integer hIndex;

    /**
     * g指数
     */
    private Integer gIndex;

    /**
     * 篇均被引
     */
    private Double avgCite;

    /**
     * 总论文数
     */
    private Integer paperNum;


}
