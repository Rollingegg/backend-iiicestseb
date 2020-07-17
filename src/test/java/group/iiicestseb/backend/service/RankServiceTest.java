package group.iiicestseb.backend.service;

import group.iiicestseb.backend.constant.rank.RankType;
import group.iiicestseb.backend.vo.rank.AuthorRankVO;
import group.iiicestseb.backend.vo.rank.RankOverviewVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * @author wengpuhong
 * @date 2020/7/15 0:38
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class RankServiceTest {

    @Resource(name = "Rank")
    private RankService rankService;

    @Test
    public void getRank() {
        AuthorRankVO authorRankVO = rankService.getRank(1,10, RankType.PAPER_NUM);
        System.out.println(authorRankVO);
        Assert.notNull(authorRankVO,"获取排名失败");
    }

    @Test
    public void getOverView() {
        RankOverviewVO rankOverviewVO = rankService.getOverView();
        System.out.println(rankOverviewVO);
        Assert.notNull(rankOverviewVO,"获取排名首页失败");
    }
}