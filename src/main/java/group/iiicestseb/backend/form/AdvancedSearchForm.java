package group.iiicestseb.backend.form;

import group.iiicestseb.backend.exception.paper.PaperFormException;
import group.iiicestseb.backend.exception.paper.PaperTypeException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * 高级检索表单
 * @author wph
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdvancedSearchForm {
    public static final String CHRON_DATE = "chron_date";
    public static final String ALL = "all";
    public static final String AFFILIATION ="affiliation_name";
    public static final String TITLE ="title";
    public static final String ABSTRACT ="paper_abstract";
    public static final String DOI ="doi";
    public static final String AUTHOR ="author_name";
    public static final String TERM ="term";
    public static final String ADVANCED ="advanced";
    public static final String LIMIT_ERROR= "limit必须大于0，不超过200";
    public static final String PAGE_ERROR= "页码必须大于等于0";

    /**
     * 页码
     */
    @Min(value = 0,message = PAGE_ERROR)
    private Integer page = 0;

    /**
     * 搜索类型
     */
    private String type = null;

    /**
     * all搜索关键字
     */
    private String allKeyword = null;

    /**
     * 文章标题关键字
     */
    private String titleKeyword = null;

    /**
     * 文章摘要关键字
     */
    private String paperAbstractKeyword = null;

    /**
     * doi关键字
     */
    private String doiKeyword = null;

    /**
     * 作者关键字
     */
    private String authorKeyword = null;

    /**
     * 机构关键字
     */
    private String affiliationKeyword = null;

    /**
     * 术语
     */
    private String termKeyword = null;

    /**
     * 搜索个数
     */
    @Min(value = 1,message = LIMIT_ERROR)
    @Max(value = 200,message = LIMIT_ERROR)
    private Integer limit = 50;

    /**
     * 出版年份最小值
     */
    String chronDateMinKeyword = "1900-01-01";

    /**
     * 出版年份最大值
     */
    //@DateTimeFormat(pattern = "yyyy")
    String chronDateMaxKeyword = "2100-12-31";

    public void isValid(){

        if( !(ALL.equals(this.type) ||
                AFFILIATION.equals(this.type) ||
                AUTHOR.equals(this.type) ||
                ABSTRACT.equals(this.type) ||
                TITLE.equals(this.type) ||
                TERM.equals(this.type) ||
                DOI.equals(this.type) ||
                ADVANCED.equals(this.type) ||
                CHRON_DATE.equals(this.type)
                )){
            throw new PaperTypeException();
        }
        if(this.getTermKeyword()== null &&
                this.getAuthorKeyword()== null &&
                this.getDoiKeyword()== null &&
                this.getTitleKeyword() == null &&
                this.getPaperAbstractKeyword()== null &&
                this.getAffiliationKeyword()== null  &&
                this.getAllKeyword()==null){
                throw new PaperFormException();
        }

    }
}
