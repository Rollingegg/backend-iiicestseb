package group.iiicestseb.backend.vo.rank;

import lombok.Data;

/**
 * 排名页面首页信息
 * @author wengpuhong
 * @date 2020/7/15 15:40
 */
@Data
public class RankOverviewVO {
    /**
     * 前三h指数作者
     */
    AuthorRankVO hRank;
    /**
     * 前三g指数作者
     */
    AuthorRankVO gRank;
    /**
     * 前三平均被引数作者
     */
    AuthorRankVO avgCiteRank;
    /**
     * 前三论文数作者
     */
    AuthorRankVO paperNumRank;
}
