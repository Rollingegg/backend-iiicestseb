package group.iiicestseb.backend.service;

import group.iiicestseb.backend.constant.rank.RankType;
import group.iiicestseb.backend.vo.rank.AuthorRankVO;
import group.iiicestseb.backend.vo.rank.RankOverviewVO;

/**
 * 排名
 * @author kenny
 */
public interface RankService {

    /**
     * 获取排名页面的首页展示
     * @return
     */
    RankOverviewVO getOverView();


    /**
     * 获取学术排名
     * @param page 页码
     * @param size 长度
     * @param rankType 排名类型
     * @return h指数排名信息
     */
    AuthorRankVO getRank(Integer page, Integer size, RankType rankType);



}
