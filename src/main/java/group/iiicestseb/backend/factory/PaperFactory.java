package group.iiicestseb.backend.factory;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperDetail;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.paper.PaperBasicVO;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author jh
 * @date 2020/3/29
 */
public class PaperFactory {

    public static PaperOverview toPaperOverview(Paper paper){
        PaperOverview overview = new PaperOverview();
        overview.setId(paper.getId());
        overview.setArticleId(paper.getArticleId());
        overview.setAuthorKeywords(paper.getAuthorKeywords());
        overview.setChronDate(paper.getChronDate());
        overview.setTitle(paper.getTitle());
        overview.setPaperAbstract(paper.getPaperAbstract());
        return overview;
    }

    public static Collection<PaperOverview> toPaperOverviewBatch(Collection<Paper> papers){
        Collection<PaperOverview> result = new LinkedList<>();
        for (Paper p : papers) {
            result.add(toPaperOverview(p));
        }
        return result;
    }

    public static PaperDetail packageDetail(Paper paper, Collection<AuthorInfoVO> authors, Conference conference, Collection<Term> terms, Collection<Reference> references) {
        PaperDetail detail = new PaperDetail();
        detail.setPaper(paper);
        detail.setAuthorList(authors);
        detail.setConference(conference);
        detail.setTermList(terms);
        detail.setReferenceList(references);
        return detail;
    }

    public static PaperBasicVO toPaperBasicVO(Paper paper) {
        PaperBasicVO paperBasicVO = new PaperBasicVO();
        paperBasicVO.setChronDate(paper.getChronDate());
        paperBasicVO.setId(paper.getId());
        paperBasicVO.setTitle(paper.getTitle());
        return paperBasicVO;
    }
}
