package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

/**
 * @author jh
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge implements Comparable<Edge> {

    /**
     * 边名称
     */
    private String name;

    /**
     * 边大小
     */
    Double weight;

    /**
     * 起始点uuid
     */
    String source;

    /**
     * 目的点uuid
     */
    String target;

    /**
     * 边相关业务内容
     */
    Object content;

    @Override
    public int compareTo(@NotNull Edge o) {
        return this.weight > o.getWeight() ? 1 : this.weight.equals(o.weight) ? 0 : -1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Edge that = (Edge) o;
        return this.source.equals(that.source) && this.target.equals(that.target);
    }

}
