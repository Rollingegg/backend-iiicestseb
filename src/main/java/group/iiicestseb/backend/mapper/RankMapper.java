package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.AuthorStatistics;
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

    @ResultType(AuthorStatistics.class)
    @Select("select * from author_statistics  order by h_index desc ,author_id desc limit #{start},#{end}")
    List<AuthorStatistics> getRankByHScore(Integer start,Integer end);

    @ResultType(AuthorStatistics.class)
    @Select("select * from author_statistics  order by g_index desc ,author_id desc limit #{start},#{end}")
    List<AuthorStatistics> getRankByGScore(Integer start,Integer end);

    @ResultType(AuthorStatistics.class)
    @Select("select * from author_statistics  order by avg_cite desc ,author_id desc limit #{start},#{end}")
    List<AuthorStatistics> getRankByAvgCite(Integer start,Integer end);

    @ResultType(AuthorStatistics.class)
    @Select("select * from author_statistics  order by paper_num desc ,author_id desc limit #{start},#{end}")
    List<AuthorStatistics> getRankByPaperNum(Integer start,Integer end);

//    /**
//     * 根据对应的术语找寻相关论文的所有作者
//     * 计算搜索年份内的平均被引数作为排名参考
//     * @param rankForm 排名请求表单
//     * @return 学者排名列表
//     */
//    @Select("select concat(#{startTime},'-',#{endTime}) as time," +
//            " au.id, au.name,au.affiliation_id,au.last_name,au.first_name," +
//            " avg(p.citation_count_paper) as citationAvg   from paper_authors as pa,author as au,paper as p,paper_term as pt,term as t " +
//            " where (t.name = '' or t.name = #{term}) and t.id = pt.term_id" +
//            " and pt.paper_id = p.id and pa.paper_id = p.id and au.id = pa.paper_id " +
//            "and (p.chron_date between #{chronDateMinKeyword} and #{chronDateMaxKeyword})" +
//            " group by author_id")
//    @ResultType(AuthorHRank.class)
//    List<AuthorHRank> getAuthorRank(RankForm rankForm);



}
