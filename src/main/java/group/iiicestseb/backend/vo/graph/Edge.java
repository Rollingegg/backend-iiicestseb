package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {
    /**
     * 边id
     */
    Integer id;

    /**
     * 边名称
     */
    String name;

    /**
     * 边大小
     */
    Double weight;

    /**
     * 起始点id
     */
    Integer source ;

    /**
     * 目的点id
     */
    Integer target;

    /**
     * 边相关业务内容
     */
    Object content;

}
