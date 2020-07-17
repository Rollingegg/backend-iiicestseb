package group.iiicestseb.backend.vo.rank;

import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.AuthorStatistics;
import lombok.Data;

import java.util.Map;

/**
 * @author wph
 * 作者指数排名
 */
@Data
public class AuthorRankVO {
    /**
     * 作者H指数排名 
     */
    private Map<Author, AuthorStatistics> AuthorMap;

    /**
     * 页码
     */
    private Integer page;


}
