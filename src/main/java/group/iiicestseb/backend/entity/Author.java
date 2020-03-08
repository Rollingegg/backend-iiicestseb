package group.iiicestseb.backend.entity;

import lombok.Data;


/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Author {

    /**
     * 作者id
     */
    private Integer id;

    /**
     * 作者姓名
     */
    private String name;

    /**
     * 作者所属机构id
     */
    private Integer affiliationId;

    public Author(String name, Integer affiliationId){
        this.name = name;
        this.affiliationId = affiliationId;
    }

    public Author(Integer id, String name, Integer affiliationId) {
        this.id = id;
        this.name = name;
        this.affiliationId = affiliationId;
    }

    public Author() {
        super();
    }

}