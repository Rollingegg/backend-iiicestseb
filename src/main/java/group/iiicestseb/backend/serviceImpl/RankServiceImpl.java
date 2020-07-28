package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.constant.rank.RankType;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.mapper.RankMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.RankService;
import group.iiicestseb.backend.vo.rank.AuthorRankDataVO;
import group.iiicestseb.backend.vo.rank.AuthorRankVO;
import group.iiicestseb.backend.vo.rank.RankOverviewVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kenny
 */
@Service("Rank")
@Slf4j
public class RankServiceImpl extends ServiceImpl<RankMapper,AuthorStatistics> implements RankService {

    @Resource(name = "Regedit")
    private Regedit regedit;


    @Resource
    private RankMapper rankMapper;

    @Override
    public AuthorRankVO getRank(Integer page, Integer size, RankType rankType){
        AuthorRankVO authorRankVO = new AuthorRankVO();
        List<AuthorRankDataVO> scores = null;
        if(rankType.equals(RankType.H_INDEX)){
            scores = rankMapper.getRankByHScore((page-1)*size,page * size);
        }else if(rankType.equals(RankType.G_INDEX)){
            scores = rankMapper.getRankByGScore((page-1)*size,page * size);
        }else if(rankType.equals(RankType.AVG_CITE)){
            scores = rankMapper.getRankByAvgCite((page-1)*size,page * size);
        }else if(rankType.equals(RankType.PAPER_NUM)){
            scores = rankMapper.getRankByPaperNum((page-1)*size,page * size);
        }else if(rankType.equals(RankType.SOCIABILITY)){
            scores = rankMapper.getRankBySociability((page-1)*size,page*size);
        }
        if(CollectionUtils.isEmpty(scores)){
            throw new RuntimeException("获取排名错误");
        }
        authorRankVO.setAuthorRankDataVOList(scores);
        authorRankVO.setTotal(rankMapper.getCount());
        return authorRankVO;
    }


    @Override
    public RankOverviewVO getOverView() {
        RankOverviewVO rankOverviewVO = new RankOverviewVO();
        rankOverviewVO.setHRank(getRank(1,3,RankType.H_INDEX));
        rankOverviewVO.setGRank(getRank(1,3,RankType.G_INDEX));
        rankOverviewVO.setAvgCiteRank(getRank(1,3,RankType.AVG_CITE));
        rankOverviewVO.setPaperNumRank(getRank(1,3,RankType.PAPER_NUM));
        rankOverviewVO.setSociabilityRank(getRank(1,3,RankType.SOCIABILITY));
        return rankOverviewVO;
    }
}
