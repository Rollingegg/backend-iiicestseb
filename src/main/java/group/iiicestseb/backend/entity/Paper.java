package group.iiicestseb.backend.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;


@TableName("paper")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Paper {
    @TableId("id")
    private int id;

    @TableField("pdf_url")
    private String pdfUrl;

    @TableField("author_keywords")
    private String authorKeywords;

    @TableField("title")
    private String title;

    @TableField("paper_abstract")
    private String paperAbstract;

    @TableField("doi")
    private String doi;


    @TableField("publication_title")
    private String publicationTitle;

    @TableField("citation_count_paper")
    private Integer citationCountPaper;

    @TableField("citation_count_patent")
    private Integer citationCountPatent;

    @TableField("total_downloads")
    private Integer totalDownloads;

    @TableField("start_page")
    private String startPage;

    @TableField("end_page")
    private String endPage;

    @TableField("pub_link")
    private String pubLink;

    @TableField("issue_link")
    private String issueLink;

    @TableField("publisher")
    private String publisher;

    @TableField("conf_loc")
    private String confLoc;

    @TableField("chron_date")
    private LocalDateTime chronDate;

    @TableField("article_id")
    private Integer articleId;

    @TableField("conference_id")
    private Integer conferenceId;


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
