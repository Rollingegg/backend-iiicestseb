package group.iiicestseb.backend.vo;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wph
 */
@Data
public class SearchResultVO {

    /**
     * 论文id
     */
    private Integer id;

    /**
     * 论文标题
     */
    private String paperTitle;


    /**
     * 被引次数
     */
    private Integer citationCount;

    /**
     * 出版日期
     */
    private LocalDateTime publicationYear;

    /**
     * 作者资料列表
     */
    private CopyOnWriteArrayList<AuthorInfoVO> authorInfoList;

    public SearchResultVO(Paper paper) {
        this.id = paper.getId();
        this.paperTitle = paper.getPaperTitle();
        this.citationCount = paper.getCitationCount();
        this.publicationYear = paper.getPublicationYear();

    }
}