package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.vo.rank.AuthorRankDataVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wph
 * @date 2020/05/17
 */
@Mapper
public interface RankMapper extends BaseMapper<AuthorStatistics>{

    @ResultType(AuthorRankDataVO.class)
    @Select("select au.id as id, au.name as name, aff.id as affiliationId, aff.name as affiliationName ,aus.h_index as value " +
            "from author_statistics as aus,author as au,affiliation as aff " +
            "where au.id = aus.author_id and au.affiliation_id = aff.id " +
            "order by aus.h_index desc ,aus.author_id desc limit #{start},#{end}")
    List<AuthorRankDataVO> getRankByHScore(Integer start,Integer end);

    @ResultType(AuthorRankDataVO.class)
    @Select("select au.id as id, au.name as name, aff.id as affiliationId, aff.name as affiliationName ,aus.g_index as value " +
            "from author_statistics as aus,author as au,affiliation as aff " +
            "where au.id = aus.author_id and au.affiliation_id = aff.id " +
            "order by aus.g_index desc ,aus.author_id desc limit #{start},#{end}")
    List<AuthorRankDataVO> getRankByGScore(Integer start,Integer end);

    @ResultType(AuthorRankDataVO.class)
    @Select("select au.id as id, au.name as name, aff.id as affiliationId, aff.name as affiliationName ,aus.avg_cite as value " +
            "from author_statistics as aus,author as au,affiliation as aff " +
            "where au.id = aus.author_id and au.affiliation_id = aff.id " +
            "order by aus.avg_cite desc ,aus.author_id desc limit #{start},#{end}")
    List<AuthorRankDataVO> getRankByAvgCite(Integer start,Integer end);

    @ResultType(AuthorRankDataVO.class)
    @Select("select au.id as id, au.name as name, aff.id as affiliationId, aff.name as affiliationName ,aus.paper_num as value " +
            "from author_statistics as aus,author as au,affiliation as aff " +
            "where au.id = aus.author_id and au.affiliation_id = aff.id " +
            "order by aus.paper_num desc ,aus.author_id desc limit #{start},#{end}")
    List<AuthorRankDataVO> getRankByPaperNum(Integer start,Integer end);

    @ResultType(AuthorRankDataVO.class)
    @Select("select au.id as id, au.name as name, aff.id as affiliationId, aff.name as affiliationName ,aup.page_rank as value " +
            "from author_page_rank as aup,author as au,affiliation as aff " +
            "where au.id = aup.author_id and au.affiliation_id = aff.id " +
            "order by aup.page_rank desc ,aup.author_id desc limit #{start},#{end}")
    List<AuthorRankDataVO> getRankBySociability(Integer start,Integer end);

    @Select("select count(*) from author_statistics")
    Integer getCount();



}
