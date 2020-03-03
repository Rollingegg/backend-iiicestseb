package group.iiicestseb.backend.vo;

import group.iiicestseb.backend.entity.Paper;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 文献管理页面 文献的详细信息
 * @author wph
 */
@Data
public class PaperInfoVO {
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
    private String startPage;

    /**
     * 结束页
     */
    private String endPage;

    /**
     * 作者关键字
     */
    private String authorKeywords;

    /**
     * document_identifier （讨论中不知道啥意思的项，保留英文）
     */
    private String documentIdentifier;

    /**
     * 出版社名称
     */
    private String publisherName;

    /**
     * 会议名称
     */
    private String conferenceName;

    public PaperInfoVO(){}
    public PaperInfoVO(Paper paper) {
        // TODO: 拼凑文章关键词
//        this.authorKeywords = paper.getAuthorKeywords();
        this.citationCount = paper.getCitationCount();
        this.conferenceId = paper.getConferenceId();
        this.documentIdentifier = paper.getDocumentIdentifier();
        this.doi = paper.getDoi();
        this.endPage = paper.getEndPage();
        this.startPage = paper.getStartPage();
        this.id = paper.getId();
        this.pdfLink = paper.getPdfLink();
        this.paperAbstract = paper.getPaperAbstract();
        this.paperTitle = paper.getPaperTitle();
        this.publicationYear = paper.getPublicationYear();
        this.referenceCount = paper.getReferenceCount();
        this.publicationTitle = paper.getPublicationTitle();
        this.publisherId = paper.getPublisherId();
    }
}
