package group.iiicestseb.backend.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Paper {
    private Integer id;

    private String publicationTitle;

    private Integer publisherId;

    private Integer conferenceId;

    private String pdfLink;

    private String doi;

    private String paperTitle;

    private String paperAbstract;

    private Integer referenceCount;

    private Integer citationCount;

    private LocalDateTime publicationYear;

    private Integer startPage;

    private Integer endPage;

    private String authorKeywords;

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