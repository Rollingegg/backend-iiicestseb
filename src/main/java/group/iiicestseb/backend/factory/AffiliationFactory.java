package group.iiicestseb.backend.factory;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.exception.paper.StatisticException;
import group.iiicestseb.backend.vo.affiliation.AffiliationActiveInTerm;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author jh
 * @date 2020/4/5
 */
public class AffiliationFactory {

    public static final String UNMATCHED_SIZE = "打包参数出错";

    public static Collection<AffiliationActiveInTerm> packageAffiliationActiveInTerm(Map<Integer, AffiliationActiveInTerm.AffWithScore> affScores, Collection<Affiliation> affiliations) {
        if (affScores.size() != affiliations.size()) {
            throw new StatisticException(UNMATCHED_SIZE);
        }
        ArrayList<AffiliationActiveInTerm> results = new ArrayList<>(affiliations.size());
        for (Affiliation a : affiliations) {
            AffiliationActiveInTerm.AffWithScore as = affScores.get(a.getId());
            results.add(new AffiliationActiveInTerm(a.getId(), a.getName(), as.getScore(), as.getPaperNum()));
        }
        return results;
    }

}
