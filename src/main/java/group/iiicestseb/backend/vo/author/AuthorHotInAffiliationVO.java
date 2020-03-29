package group.iiicestseb.backend.vo.author;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/3/25
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorHotInAffiliationVO {

    /**
     * 作者id
     */
    private int id;

    /**
     * 作者名称
     */
    private String name;


    /**
     * 发表文献数量
     */
    private Integer publishNum;
}
