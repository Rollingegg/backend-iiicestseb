package group.iiicestseb.backend.vo;

import lombok.Data;


/**
 * 机构页面 机构详细信息
 * @author wph
 * @date 2020/3/2
 */
@Data
public class AffiliationInfoVO {
    /**
     * 机构id
     */
    private Integer id;

    /**
     * 机构名
     */
    private String name;

    /**
     * 机构成员数量
     */
    private Integer authorNum;

    /**
     * 机构文章数量
     */
    private Integer paperNum;
}
