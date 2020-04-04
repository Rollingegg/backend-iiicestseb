package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.AuthorStatistics;
import group.iiicestseb.backend.entity.PaperStatistics.AuthorPaperCites;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author jh
 * @date 2020/4/3
 */
@Mapper
public interface AuthorStatisticsMapper extends BaseMapper<AuthorStatistics> {

    /**
     * 先查找所有作者发表的论文的引用数
     *
     * @return 引用数集合
     */
    @Select("select pa.author_id, p.citation_count_paper as cite, c.name as conference " +
            "from paper p, " +
            "     paper_authors pa, " +
            "     conference c " +
            "where p.id = pa.paper_id " +
            "  and pa.author_id in (select a.id from author a) " +
            "  and c.id = p.conference_id " +
            "order by cite desc ")
    @ResultType(AuthorPaperCites.class)
    Collection<AuthorPaperCites> selectAllAuthorPaperCites();

    /**
     * 重算所有作者的统计指数
     *
     * @return 重算的行数
     */
    @Update("<script>" +
            "insert into author_statistics(author_id, h_index, g_index) " +
            "   select p.id, " +
            "       p.citation_count_paper*0.7/200 " +
            "       + p.total_downloads*0.1/2000" +
            "       - (year(now())-year(p.chron_date))*0.1/15 " +
            "   from paper p where p.id in " +
            "   (<foreach collection='ids' item='i' separator=',' >" +
            "   #{i}" +
            "   </foreach>) " +
            "   on duplicate key update " +
            "   score=values(score)" +
            "</script>")
    Integer reComputeAuthorScore();

    /**
     * 批量插入作者统计信息
     *
     * @param authorStatistics 作者统计信息
     * @return 行数
     */
    @Update("<script>" +
            "insert into author_statistics(author_id, h_index, g_index, avg_cite, paper_num, ase_paper_num, icse_paper_num)  " +
            "   values (" +
            "   <foreach collection='asClc' item='i' separator='), (' >" +
            "   #{i.authorId}, #{i.hIndex}, #{i.gIndex}, #{i.avgCite}, #{i.paperNum}, #{i.asePaperNum}, #{i.icsePaperNum} " +
            "   </foreach>) " +
            "   on duplicate key update " +
            "   h_index=values(h_index), g_index=values(g_index), " +
            "   avg_cite=values(avg_cite), paper_num=values(paper_num)" +
            "</script>")
    Integer insertOrUpdateBatch(@Param("asClc") Collection<AuthorStatistics> authorStatistics);

    /**
     * 根据作者id查找作者统计信息
     *
     * @param authorId 作者id
     * @return 作者统计信息
     */
    @Select("select * from author_statistics where author_id = #{authorId}")
    @ResultType(AuthorStatistics.class)
    AuthorStatistics selectByAuthorId(@Param("authorId") Integer authorId);

}
