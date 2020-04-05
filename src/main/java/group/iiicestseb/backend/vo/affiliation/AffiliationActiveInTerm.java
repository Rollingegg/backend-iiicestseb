package group.iiicestseb.backend.vo.affiliation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/4/5
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AffiliationActiveInTerm {

    Integer affiliationId;

    String name;

    Double score;

    Integer paperNum;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AffWithScore {
        Integer id;
        Double score;
        Integer paperNum;
    }

}
