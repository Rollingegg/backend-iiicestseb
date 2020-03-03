package group.iiicestseb.backend.entity;


import lombok.Data;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Publish {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 作者id
     */
    private Integer authorId;

    /**
     * 文献id
     */
    private Integer paperId;

    public Publish() {
        super();
    }

    public Publish(Integer id, Integer authorId, Integer paperId) {
        this.id = id;
        this.authorId = authorId;
        this.paperId = paperId;
    }

    public Publish(Paper paper, Author author){
        this.authorId = author.getId();
        this.paperId = paper.getId();
    }

}