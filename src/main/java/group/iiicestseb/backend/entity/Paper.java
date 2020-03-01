package group.iiicestseb.backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Paper {

    /**
     * 文献id
     */
    private Integer id;

    /**
     * 文献所属出版刊物名称
     */
    private String publicationTitle;

    /**
     * 刊物所属出版社id
     */
    private Integer publisherId;

    /**
     * 文献所属会议id
     */
    private Integer conferenceId;

    /**
     * 文献pdf连接
     */
    private String pdfLink;

    /**
     * DOI
     */
    private String doi;

    /**
     * 文献标题
     */
    private String paperTitle;

    /**
     * 摘要
     */
    private String paperAbstract;

    /**
     * 参考文献数
     */
    private Integer referenceCount;

    /**
     * 文献被引用次数
     */
    private Integer citationCount;

    /**
     * 出版年份
     */
    private LocalDateTime publicationYear;

    /**
     * 起始页
     */
    private Integer startPage;

    /**
     * 结束页
     */
    private Integer endPage;

    /**
     * 作者关键字
     */
    private String authorKeywords;

    /**
     * document_identifier （讨论中不知道啥意思的项，保留英文）
     */
    private String documentIdentifier;

    public Paper(Integer id, String publicationTitle, Integer publisherId, Integer conferenceId, String pdfLink, String doi, String paperTitle, String paperAbstract, Integer referenceCount, Integer citationCount, LocalDateTime publicationYear, Integer startPage, Integer endPage, String authorKeywords, String documentIdentifier) {
        this.id = id;
        this.publicationTitle = publicationTitle;
        this.publisherId = publisherId;
        this.conferenceId = conferenceId;
        this.pdfLink = pdfLink;
        this.doi = doi;
        this.paperTitle = paperTitle;
        this.paperAbstract = paperAbstract;
        this.referenceCount = referenceCount;
        this.citationCount = citationCount;
        this.publicationYear = publicationYear;
        this.startPage = startPage;
        this.endPage = endPage;
        this.authorKeywords = authorKeywords;
        this.documentIdentifier = documentIdentifier;
    }

    public Paper() {
        super();
    }

}