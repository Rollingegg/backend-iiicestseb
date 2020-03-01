package group.iiicestseb.backend.entity;


import lombok.Data;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Publisher {

    /**
     * 作者发表文献_关系id
     */
    private Integer id;

    private String name;

    public Publisher(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Publisher() {
        super();
    }

}