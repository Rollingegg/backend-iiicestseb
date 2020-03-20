package group.iiicestseb.backend.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.Collection;
import java.util.Objects;

@Entity
@Table(name = "paper", schema = "iiicestseb")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Basic
    @Column(name = "pdf_url")
    private String pdfUrl;

    @Basic
    @Column(name = "author_keywords")
    private String authorKeywords;

    @Basic
    @Column(name = "title")
    private String title;

    @Basic
    @Column(name = "paper_abstract")
    private String paperAbstract;

    @Basic
    @Column(name = "doi")
    private String doi;

    @Basic
    @Column(name = "publication_title")
    private String publicationTitle;

    @Basic
    @Column(name = "citation_count_paper")
    private Integer citationCountPaper;

    @Basic
    @Column(name = "citation_count_patent")
    private Integer citationCountPatent;

    @Basic
    @Column(name = "total_downloads")
    public Integer getTotalDownloads() {
        return totalDownloads;
    }
    private Integer totalDownloads;

    @Basic
    @Column(name = "start_page")
    private String startPage;

    @Basic
    @Column(name = "end_page")
    private String endPage;

    @Basic
    @Column(name = "pub_link")
    private String pubLink;

    @Basic
    @Column(name = "issue_link")
    private String issueLink;

    @Basic
    @Column(name = "publisher")
    private Integer publisher;

    @Basic
    @Column(name = "conf_loc")
    private String confLoc;

    @Basic
    @Column(name = "chron_date")
    private Date chronDate;

    @Basic
    @Column(name = "article_id")
    private Integer articleId;

    @Basic
    @Column(name = "conference_id")
    private Integer conferenceId;

//    @ManyToOne
//    @JoinColumn(name = "conference_id", referencedColumnName = "id")
//    private Conference conferenceByConferenceId;
//
//    @OneToMany(mappedBy = "paperByPaperId")
//    private Collection<PaperTerm> paperTermsById;
//
//    @ManyToOne
//    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
//    private Reference referenceByArticleId;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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
                Objects.equals(chronDate, that.chronDate)
                && Objects.equals(articleId, that.articleId)
                &&Objects.equals(conferenceId, that.conferenceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pdfUrl, authorKeywords, title, paperAbstract, doi, publicationTitle, citationCountPaper, citationCountPatent, totalDownloads, startPage, endPage, pubLink, issueLink, publisher, confLoc, chronDate,articleId, conferenceId);
    }

}
