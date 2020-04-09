package group.iiicestseb.backend.vo.affiliation;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 机构页面 机构详细信息
 * @author wph
 * @date 2020/3/2
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationInfoVO {
    /**
     * 机构id
     */
    @TableField("id")
    private Integer id;

    /**
     * 机构名
     */
    @TableField("name")
    private String name;

    /**
     * 机构成员数量
     */
    @TableField("authorNum")
    private Integer authorNum;

    /**
     * 机构文章数量
     */
    @TableField("paperNum")
    private Integer paperNum;
}
