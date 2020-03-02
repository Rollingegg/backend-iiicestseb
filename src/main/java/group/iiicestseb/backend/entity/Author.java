package group.iiicestseb.backend.entity;

import lombok.Data;

@Data
public class Author {
    private Integer id;

    private String name;

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