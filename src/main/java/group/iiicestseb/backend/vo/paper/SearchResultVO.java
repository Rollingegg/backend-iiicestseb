package group.iiicestseb.backend.vo.paper;


import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Term;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author wph
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultVO {

    /**
     * 论文id
     */
    private Integer id;

    /**
     * 论文标题
     */
    private String title;

    /**
     * 摘要
     */
    private String paperAbstract;

    /**
     * pdf链接
     */
    private String pdfUrl;

    /**
     * 被引次数
     */
    private Integer citationCountPaper;

    /**
     * 出版日期
     */
    private LocalDateTime publicationYear;

    /**
     * 作者资料列表
     */
    private List<Author> authorList;

    /**
     * 文献术语
     */
    private List<Term> termsList;

}