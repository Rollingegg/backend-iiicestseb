package group.iiicestseb.backend.vo.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 作者详细信息VO
 * @author wph
 * @date 2020/3/2
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorInfoVO {

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


}
