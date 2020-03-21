package group.iiicestseb.backend.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * 高级检索表单
 * @author wph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    /**
     *
     */
    private Integer limit = 50;
}
