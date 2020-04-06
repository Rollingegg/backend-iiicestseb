package group.iiicestseb.backend.vo.graph;


import group.iiicestseb.backend.utils.NumberUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
    private String centerId;

    /**
     * 图名称
     */
    private String name;

    /**
     * 点集合
     */
    private Collection<Vertex> vertexes;

    /**
     * 边集合
     */
    private Collection<Edge> edges;

    public void normalize() {
        normalizeVertices();
        normalizeEdges();
    }

    private void normalizeVertices() {
        Map<String, Double> maxMap = new HashMap<>(10);
        Map<String, Double> minMap = new HashMap<>(10);
        Map<String, Double> sectionMap = new HashMap<>(10);
        for (Vertex v : vertexes) {
            Double vSize = v.getSize();
            Double max = maxMap.getOrDefault(v.getType(), Double.MIN_VALUE);
            Double min = minMap.getOrDefault(v.getType(), Double.MAX_VALUE);
            maxMap.put(v.getType(), vSize > max ? vSize : max);
            minMap.put(v.getType(), vSize < min ? vSize : min);
        }
        for (String key : maxMap.keySet()) {
            sectionMap.put(key,
                    ((int) ((maxMap.get(key) - minMap.get(key)) * 1000) == 0) ?
                            1 : (maxMap.get(key) - minMap.get(key)));
        }
        for (Vertex v : vertexes) {
            String type = v.getType();
            v.setSize(NumberUtil.sigmoid(5 * (v.getSize() - minMap.get(type)) / sectionMap.get(type)));
        }
    }

    private void normalizeEdges() {
        double max = 0.0;
        double min = 0.0;
        double section;
        for (Edge e : edges) {
            Double temp = e.getWeight();
            max = temp > max ? temp : max;
            min = temp < min ? temp : min;
        }
        section = ((int) ((max - min) * 1000) == 0) ? 1 : (max - min);
        for (Edge e : edges) {
            e.setWeight(NumberUtil.sigmoid(5 * (e.getWeight() - min) / section));
        }
    }

}
