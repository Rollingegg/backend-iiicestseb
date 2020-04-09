package group.iiicestseb.backend.vo.author;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 只有作者名的vo
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthorInAffiliationVO {

    /**
     * 作者id
     */
    private Integer id;

    /**
     * 作者名
     */
    private String name;
}
