package group.iiicestseb.backend.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "paper", schema = "iiicestseb", catalog = "")
public class Paper {
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
    private Integer conferenceId;
    private Conference conferenceByConferenceId;
    private Collection<PaperTerm> paperTermsById;
    private Reference referenceByArticleId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pdf_url")
    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
    }

    @Basic
    @Column(name = "author_keywords")
    public String getAuthorKeywords() {
        return authorKeywords;
    }

    public void setAuthorKeywords(String authorKeywords) {
        this.authorKeywords = authorKeywords;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "paper_abstract")
    public String getPaperAbstract() {
        return paperAbstract;
    }

    public void setPaperAbstract(String paperAbstract) {
        this.paperAbstract = paperAbstract;
    }

    @Basic
    @Column(name = "doi")
    public String getDoi() {
        return doi;
    }

    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Basic
    @Column(name = "publication_title")
    public String getPublicationTitle() {
        return publicationTitle;
    }

    public void setPublicationTitle(String publicationTitle) {
        this.publicationTitle = publicationTitle;
    }

    @Basic
    @Column(name = "citation_count_paper")
    public Integer getCitationCountPaper() {
        return citationCountPaper;
    }

    public void setCitationCountPaper(Integer citationCountPaper) {
        this.citationCountPaper = citationCountPaper;
    }

    @Basic
    @Column(name = "citation_count_patent")
    public Integer getCitationCountPatent() {
        return citationCountPatent;
    }

    public void setCitationCountPatent(Integer citationCountPatent) {
        this.citationCountPatent = citationCountPatent;
    }

    @Basic
    @Column(name = "total_downloads")
    public Integer getTotalDownloads() {
        return totalDownloads;
    }

    public void setTotalDownloads(Integer totalDownloads) {
        this.totalDownloads = totalDownloads;
    }

    @Basic
    @Column(name = "start_page")
    public String getStartPage() {
        return startPage;
    }

    public void setStartPage(String startPage) {
        this.startPage = startPage;
    }

    @Basic
    @Column(name = "end_page")
    public String getEndPage() {
        return endPage;
    }

    public void setEndPage(String endPage) {
        this.endPage = endPage;
    }

    @Basic
    @Column(name = "pub_link")
    public String getPubLink() {
        return pubLink;
    }

    public void setPubLink(String pubLink) {
        this.pubLink = pubLink;
    }

    @Basic
    @Column(name = "issue_link")
    public String getIssueLink() {
        return issueLink;
    }

    public void setIssueLink(String issueLink) {
        this.issueLink = issueLink;
    }

    @Basic
    @Column(name = "publisher")
    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    @Basic
    @Column(name = "conf_loc")
    public String getConfLoc() {
        return confLoc;
    }

    public void setConfLoc(String confLoc) {
        this.confLoc = confLoc;
    }

    @Basic
    @Column(name = "chron_date")
    public Date getChronDate() {
        return chronDate;
    }

    public void setChronDate(Date chronDate) {
        this.chronDate = chronDate;
    }

    @Basic
    @Column(name = "article_id")
    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    @Basic
    @Column(name = "conference_id")
    public Integer getConferenceId() {
        return conferenceId;
    }

    public void setConferenceId(Integer conferenceId) {
        this.conferenceId = conferenceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Paper that = (Paper) o;
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
                Objects.equals(articleId, that.articleId) &&
                Objects.equals(conferenceId, that.conferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pdfUrl, authorKeywords, title, paperAbstract, doi, publicationTitle, citationCountPaper, citationCountPatent, totalDownloads, startPage, endPage, pubLink, issueLink, publisher, confLoc, chronDate, articleId, conferenceId);
    }

    @ManyToOne
    @JoinColumn(name = "conference_id", referencedColumnName = "id")
    public Conference getConferenceByConferenceId() {
        return conferenceByConferenceId;
    }

    public void setConferenceByConferenceId(Conference conferenceByConferenceId) {
        this.conferenceByConferenceId = conferenceByConferenceId;
    }

    @OneToMany(mappedBy = "paperByPaperId")
    public Collection<PaperTerm> getPaperTermsById() {
        return paperTermsById;
    }

    public void setPaperTermsById(Collection<PaperTerm> paperTermsById) {
        this.paperTermsById = paperTermsById;
    }

    @ManyToOne
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    public Reference getReferenceByArticleId() {
        return referenceByArticleId;
    }

    public void setReferenceByArticleId(Reference referenceByArticleId) {
        this.referenceByArticleId = referenceByArticleId;
    }
}
