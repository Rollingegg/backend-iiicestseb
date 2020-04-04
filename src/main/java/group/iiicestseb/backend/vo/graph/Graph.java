package group.iiicestseb.backend.vo.graph;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author wph
 * 图谱VO
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Graph {
    /**
     * 图名称
     */
    int name;

    /**
     * 点集合
     */
    Collection<Vertex> vertexes;

    /**
     * 边集合
     */
    Collection<Edge> edges;


}
