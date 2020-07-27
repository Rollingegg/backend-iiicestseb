package group.iiicestseb.backend.vo.rank;

import lombok.Data;

import java.util.List;

/**
 * @author wph
 * 作者指数排名
 */
@Data
public class AuthorRankVO {
    /**
     * 作者H指数排名 
     */
    private List<AuthorRankDataVO> authorRankDataVOList;

    /**
     * 页码
     */
    private Integer page;


}
