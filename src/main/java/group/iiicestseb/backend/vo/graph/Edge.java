package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 边VO
 *
 * @author wph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Edge {

    /**
     * 边名称
     */
    String name;

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

}
