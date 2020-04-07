package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.jetbrains.annotations.NotNull;

/**
 * @author jh
 */
@Data
@AllArgsConstructor
public class Vertex implements Comparable<Vertex> {
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

    @Override
    public int compareTo(@NotNull Vertex o) {
        int result = this.type.compareTo(o.type);
        if (result == 0) {
            return this.size > o.size ? 1 : this.size.equals(o.size) ? 0 : -1;
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        return this.id.equals(((Vertex) o).id);
    }

}
