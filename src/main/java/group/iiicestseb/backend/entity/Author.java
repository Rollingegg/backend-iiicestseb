package group.iiicestseb.backend.entity;

public class Author {
    private Integer id;

    private String name;

    private Integer affiliationId;

    public Author(Integer id, String name, Integer affiliationId) {
        this.id = id;
        this.name = name;
        this.affiliationId = affiliationId;
    }

    public Author() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAffiliationId() {
        return affiliationId;
    }

    public void setAffiliationId(Integer affiliationId) {
        this.affiliationId = affiliationId;
    }
}