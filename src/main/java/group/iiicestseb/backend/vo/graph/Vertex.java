package group.iiicestseb.backend.vo.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 点VO
 * @author wph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
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
    String name ;

    /**
     * 点大小
     */
    Double size;

    /**
     * 点相关业务内容，根据type变化
     */
    Object content;

}
