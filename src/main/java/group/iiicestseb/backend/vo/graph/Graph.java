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
     * 中心点uuid
     */
    String centerId;

    /**
     * 图名称
     */
    String name;

    /**
     * 点集合
     */
    Collection<Vertex> vertexes;

    /**
     * 边集合
     */
    Collection<Edge> edges;

//    /**
//     * 区间：点最小值
//     */
//    HashMap<String,Double> min = new HashMap<>();
//
//    /**
//     * 区间：点最大值
//     */
//    HashMap<String,Double> max = new HashMap<>();
}
