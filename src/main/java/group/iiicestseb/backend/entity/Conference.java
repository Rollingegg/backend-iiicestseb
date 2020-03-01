package group.iiicestseb.backend.entity;
import lombok.Data;


/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Conference {

    /**
     * 会议id
     */
    private Integer id;

    /**
     * 会议名称
     */
    private String name;

    public Conference(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Conference() {
        super();
    }
}