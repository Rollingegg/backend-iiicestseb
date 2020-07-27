package group.iiicestseb.backend.vo.rank;

import lombok.Data;

/**
 * @author wengpuhong
 * @date 2020/7/27 12:41
 */
@Data
public class AuthorRankDataVO {

    private Integer id;

    private String name;

    private String affiliationId;

    private String affiliationName;

    private Double value;
}
