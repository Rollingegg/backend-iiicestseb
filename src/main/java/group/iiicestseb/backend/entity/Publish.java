package group.iiicestseb.backend.entity;

public class Publish {
    private Integer id;

    private Integer authorId;

    private Integer paperId;

    public Publish(Integer id, Integer authorId, Integer paperId) {
        this.id = id;
        this.authorId = authorId;
        this.paperId = paperId;
    }

    public Publish() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getPaperId() {
        return paperId;
    }

    public void setPaperId(Integer paperId) {
        this.paperId = paperId;
    }
}