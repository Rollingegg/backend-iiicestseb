package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 点VO
 *
 * @author wph
 */
@Data
@AllArgsConstructor
public class Vertex {
    /**
     * 点uuid
     */
    String id;

    /**
     * 点类型
     */
    String type;

    /**
     * 点名称
     */
    String name;

    /**
     * 点大小
     */
    Double size;

    /**
     * 点相关业务内容，根据type变化
     */
    Object content;

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
