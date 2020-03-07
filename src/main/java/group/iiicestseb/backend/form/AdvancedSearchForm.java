package group.iiicestseb.backend.form;

import lombok.Data;

/**
 * 高级检索表单
 * @author wph
 */
@Data
public class AdvancedSearchForm {

    /**
     * 文章标题关键字
     */
    private String paperTitleKeyword;

    /**
     * 文章摘要关键字
     */
    private String paperAbstractKeyword;

    /**
     * doi关键字
     */
    private String doiKeyword;

    /**
     * 作者关键字
     */
    private String authorKeyword;

    /**
     * 机构关键字
     */
    private String affiliationKeyword;

    /**
     * 术语
     */
    private String termKeyword;
}
