package group.iiicestseb.backend.entity;

import lombok.Data;

/**
 * @author wph
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

    public Affiliation(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Affiliation(String name) {
        this.name = name;
    }

    public Affiliation() {
        super();
    }

}
