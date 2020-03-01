package group.iiicestseb.backend.vo;

import lombok.Data;


/**
 * 机构类
 * @author wph
 * @data 2020/3/2
 */
@Data
public class AffiliationVO {
    /**
     * 机构id
     */
    private Integer id;

    /**
     * 机构名
     */
    private String name;

    /**
     * 机构二级归属
     */
    private String secondary;

}
