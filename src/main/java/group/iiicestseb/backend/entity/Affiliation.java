package group.iiicestseb.backend.entity;

import lombok.Data;

/**
 * @author jh
 * @date 2020/2/29
 */
@Data
public class Affiliation {

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
