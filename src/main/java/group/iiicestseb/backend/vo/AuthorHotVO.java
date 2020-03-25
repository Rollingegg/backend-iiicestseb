package group.iiicestseb.backend.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/3/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorHotVO {

    /**
     * 作者id
     */
    private Integer id;

    /**
     * 作者名
     */
    private String name;

    /**
     * 机构名
     */
    private String affiliationName;

    /**
     * 发布文献数量
     */
    private Integer publishNum;

}
