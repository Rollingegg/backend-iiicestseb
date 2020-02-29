package group.iiicestseb.backend.entity;

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

    private Integer publicationYear;

    private Integer startPage;

    private Integer endPage;

    private String authorKeywords;

    private String documentIdentifer;

    public Paper(Integer id, String publicationTitle, Integer publisherId, Integer conferenceId, String pdfLink, String doi, String paperTitle, String paperAbstract, Integer referenceCount, Integer citationCount, Integer publicationYear, Integer startPage, Integer endPage, String authorKeywords, String documentIdentifer) {
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
        this.documentIdentifer = documentIdentifer;
    }

    public Paper() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle == null ? null : publicationTitle.trim();
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    public String getPdfLink() {
        return pdfLink;
    }

    public void setPdfLink(String pdfLink) {
        this.pdfLink = pdfLink == null ? null : pdfLink.trim();
    }

    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi == null ? null : doi.trim();
    }

    public String getPaperTitle() {
        return paperTitle;
    }

    public void setPaperTitle(String paperTitle) {
        this.paperTitle = paperTitle == null ? null : paperTitle.trim();
    }

    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract == null ? null : paperAbstract.trim();
    }

    public Integer getReferenceCount() {
        return referenceCount;
    }

    public void setReferenceCount(Integer referenceCount) {
        this.referenceCount = referenceCount;
    }

    public Integer getCitationCount() {
        return citationCount;
    }

    public void setCitationCount(Integer citationCount) {
        this.citationCount = citationCount;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }

    public String getAuthorKeywords() {
        return authorKeywords;
    }

    public void setAuthorKeywords(String authorKeywords) {
        this.authorKeywords = authorKeywords == null ? null : authorKeywords.trim();
    }

    public String getDocumentIdentifer() {
        return documentIdentifer;
    }

    public void setDocumentIdentifer(String documentIdentifer) {
        this.documentIdentifer = documentIdentifer == null ? null : documentIdentifer.trim();
    }
}