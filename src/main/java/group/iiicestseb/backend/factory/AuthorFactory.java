package group.iiicestseb.backend.factory;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;

import java.util.Collection;
import java.util.LinkedList;

/**
 * @author jh
 * @date 2020/3/29
 */
public class AuthorFactory {

    public static AuthorInfoVO packageAuthorInfo(Author author, Affiliation affiliation){
        AuthorInfoVO info = new AuthorInfoVO();
        info.setId(author.getId());
        info.setName(author.getName());
        info.setAffiliationId(author.getAffiliationId());
        info.setAffiliationName(affiliation.getName());
        return info;
    }

    public static Collection<PaperOverview> toPaperOverviewBatch(Collection<Paper> papers){
        Collection<PaperOverview> result = new LinkedList<>();
//        for (Paper p : papers) {
//            result.add(toPaperOverview(p));
//        }
        return result;
    }

}
