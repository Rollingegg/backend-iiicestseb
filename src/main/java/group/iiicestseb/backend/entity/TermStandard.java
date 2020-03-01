package group.iiicestseb.backend.entity;

import lombok.Data;

/**
 * @author jh
 * @date 2020/2/29
 */
@Data
public class TermStandard {

    /**
     * 术语标准id
     */
    private Integer id;

    /**
     * 术语标准名
     */
    private String name;

    public TermStandard(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public TermStandard() {
        super();
    }

}