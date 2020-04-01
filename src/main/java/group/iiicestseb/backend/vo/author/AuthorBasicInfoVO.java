package group.iiicestseb.backend.vo.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wph
 * 作者详情页面的基本作者信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorBasicInfoVO {
    /**
     * 作者id
     */
    private int id;

    /**
     * 作者名
     */
    private String name;

    /**
     * 机构id
     */
    private int affiliationId;

    /**
     * 机构名
     */
    private String affiliationName;

    /**
     * 作者总发表文献数
     */
    private int paperCount;

    /**
     * 作者总被引数
     */
    private int citationCount;

}
