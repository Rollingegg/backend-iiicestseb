package group.iiicestseb.backend.entity;


import lombok.Data;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Publisher {

    /**
     * 刊物所属出版社
     */
    private Integer id;

    private String name;

    public Publisher() {
        super();
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Publisher(Integer id, String name) {
        this.id = id;
        this.name = name;
    }



}