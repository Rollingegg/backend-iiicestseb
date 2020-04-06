package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点VO
 *
 * @author wph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vertex {
    /**
     * 点uuid
     */
    private String id;

    /**
     * 点类型
     */
    private String type;

    /**
     * 点名称
     */
    private String name;

    /**
     * 点大小
     */
    private Double size;

    /**
     * 点相关业务内容，根据type变化
     */
    private Object content;

    /**
     * 这是点的类型
     * Author("author")
     * Affiliation("affiliation")
     * Paper("paper")
     * Term("term")
     */
    public enum TYPE {
        //
        Author("author"),
        Affiliation("affiliation"),
        Paper("paper"),
        Term("term");
        public final String value;

        TYPE(String value) {
            this.value = value;
        }

    }

    public Vertex(TYPE type) {
        this.type = type.value;
    }

    public Vertex(String id, TYPE type) {
        this.id = id;
        this.type = type.value;
    }

}
