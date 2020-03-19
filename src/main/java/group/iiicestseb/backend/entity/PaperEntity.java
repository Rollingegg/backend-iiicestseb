package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "paper", schema = "iiicestseb", catalog = "")
public class PaperEntity {
    private int id;
    private String pdfUrl;
    private String authorKeywords;
    private String title;
    private String paperAbstract;
    private String doi;
    private String publicationTitle;
    private Integer citationCountPaper;
    private Integer citationCountPatent;
    private Integer totalDownloads;
    private String startPage;
    private String endPage;
    private String pubLink;
    private String issueLink;
    private Integer publisher;
    private String confLoc;
    private Date chronDate;
    private Integer articleId;
    private ConferenceEntity conferenceByConferenceId;
    private Collection<PaperTermEntity> paperTermsById;
    private ReferenceEntity referenceByArticleId;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pdf_url", nullable = true, length = -1)
    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Basic
    @Column(name = "author_keywords", nullable = true, length = -1)
    public String getAuthorKeywords() {
        return authorKeywords;
    }

    public void setAuthorKeywords(String authorKeywords) {
        this.authorKeywords = authorKeywords;
    }

    @Basic
    @Column(name = "title", nullable = true, length = 200)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "paper_abstract", nullable = true, length = -1)
    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    @Basic
    @Column(name = "doi", nullable = true, length = 40)
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Basic
    @Column(name = "publication_title", nullable = true, length = 100)
    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    @Basic
    @Column(name = "citation_count_paper", nullable = true)
    public Integer getCitationCountPaper() {
        return citationCountPaper;
    }

    public void setCitationCountPaper(Integer citationCountPaper) {
        this.citationCountPaper = citationCountPaper;
    }

    @Basic
    @Column(name = "citation_count_patent", nullable = true)
    public Integer getCitationCountPatent() {
        return citationCountPatent;
    }

    public void setCitationCountPatent(Integer citationCountPatent) {
        this.citationCountPatent = citationCountPatent;
    }

    @Basic
    @Column(name = "total_downloads", nullable = true)
    public Integer getTotalDownloads() {
        return totalDownloads;
    }

    public void setTotalDownloads(Integer totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    @Basic
    @Column(name = "start_page", nullable = true, length = 15)
    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    @Basic
    @Column(name = "end_page", nullable = true, length = 15)
    public String getEndPage() {
        return endPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    @Basic
    @Column(name = "pub_link", nullable = true, length = -1)
    public String getPubLink() {
        return pubLink;
    }

    public void setPubLink(String pubLink) {
        this.pubLink = pubLink;
    }

    @Basic
    @Column(name = "issue_link", nullable = true, length = -1)
    public String getIssueLink() {
        return issueLink;
    }

    public void setIssueLink(String issueLink) {
        this.issueLink = issueLink;
    }

    @Basic
    @Column(name = "publisher", nullable = true)
    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "conf_loc", nullable = true, length = 50)
    public String getConfLoc() {
        return confLoc;
    }

    public void setConfLoc(String confLoc) {
        this.confLoc = confLoc;
    }

    @Basic
    @Column(name = "chron_date", nullable = true)
    public Date getChronDate() {
        return chronDate;
    }

    public void setChronDate(Date chronDate) {
        this.chronDate = chronDate;
    }

    @Basic
    @Column(name = "article_id", nullable = true)
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaperEntity that = (PaperEntity) o;
        return id == that.id &&
                Objects.equals(pdfUrl, that.pdfUrl) &&
                Objects.equals(authorKeywords, that.authorKeywords) &&
                Objects.equals(title, that.title) &&
                Objects.equals(paperAbstract, that.paperAbstract) &&
                Objects.equals(doi, that.doi) &&
                Objects.equals(publicationTitle, that.publicationTitle) &&
                Objects.equals(citationCountPaper, that.citationCountPaper) &&
                Objects.equals(citationCountPatent, that.citationCountPatent) &&
                Objects.equals(totalDownloads, that.totalDownloads) &&
                Objects.equals(startPage, that.startPage) &&
                Objects.equals(endPage, that.endPage) &&
                Objects.equals(pubLink, that.pubLink) &&
                Objects.equals(issueLink, that.issueLink) &&
                Objects.equals(publisher, that.publisher) &&
                Objects.equals(confLoc, that.confLoc) &&
                Objects.equals(chronDate, that.chronDate) &&
                Objects.equals(articleId, that.articleId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pdfUrl, authorKeywords, title, paperAbstract, doi, publicationTitle, citationCountPaper, citationCountPatent, totalDownloads, startPage, endPage, pubLink, issueLink, publisher, confLoc, chronDate, articleId);
    }

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    public ConferenceEntity getConferenceByConferenceId() {
        return conferenceByConferenceId;
    }

    public void setConferenceByConferenceId(ConferenceEntity conferenceByConferenceId) {
        this.conferenceByConferenceId = conferenceByConferenceId;
    }

    @OneToMany(mappedBy = "paperByPaperId")
    public Collection<PaperTermEntity> getPaperTermsById() {
        return paperTermsById;
    }

    public void setPaperTermsById(Collection<PaperTermEntity> paperTermsById) {
        this.paperTermsById = paperTermsById;
    }

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    public ReferenceEntity getReferenceByArticleId() {
        return referenceByArticleId;
    }

    public void setReferenceByArticleId(ReferenceEntity referenceByArticleId) {
        this.referenceByArticleId = referenceByArticleId;
    }
}
