package group.iiicestseb.backend.vo.author;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者合作伙伴图关系的content
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorVertexVO {
    /**
     * 作者id
     */
    private Integer id;

    /**
     * 作者姓名
     */
    private String name;

    /**
     * 作者所属机构id
     */
    private Integer affiliationId;

    /**
     * 机构名
     */
    private String affiliationName;

    /**
     * 共事论文数
     */
    private Integer paperCount;

    /**
     * 学者评分
     */
    private Double score;
}
