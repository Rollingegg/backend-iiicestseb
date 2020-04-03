package group.iiicestseb.backend.mapper;


import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.PaperStatistics;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;

/**
 * The interface Statistics mapper.
 *
 * @author jh
 * @date 2020 /2/22
 */
@Mapper
public interface StatisticsMapper {

    /**
     * 计算并返回最热门的几个术语
     *
     * @param num 需要返回的个数
     * @return 最热门的num个术语
     */
    @Select("select t.id as id, t.name as name, count(*) as hot from term t, paper_term pt" +
            " where t.id = pt.term_id" +
            "  group by t.id order by hot desc limit #{num}")
    @Results(id = "TermsWithHotResultMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "word", property = "word", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "hot", property = "hot", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    List<TermWithHotVO> selectTermsWithHotLimit(@Param("num") Integer num);

    /**
     * 计算并返回术语的热度
     *
     * @param termId 术语id
     * @return 术语+热度
     */
    @Select("select t.id, t.name, count(*) as hot from term t, paper_term pt " +
            "where id=#{termId}")
    @ResultType(TermWithHotVO.class)
    List<TermWithHotVO> selectTermWithHot(@Param("termId") Integer termId);

    /**
     * 计算并返回发表论文最多的的num个学者和其发表的论文
     *
     * @param num num个学者
     * @return 最热门的num个学者和其发表的论文
     */
    @Select("select aut.id as id, aut.name as name, aff.id as aff_id, aff.name as aff_name, count(*) as publish_num" +
            "   from paper_authors p, author aut, affiliation aff" +
            "       where aut.id = p.author_id and aut.affiliation_id = aff.id and aut.name<>''" +
            "           group by aut.id" +
            "           order by publish_num desc" +
            "           limit #{num}")
    @Results(id = "AuthorWithPublishResultMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "aff_id", property = "affiliationId", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "aff_name", property = "affiliationName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "publish_num", property = "publishNum", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    List<AuthorHotVO> selectMaxPublishAuthorLimit(Integer num);

    /**
     * 获取在指定术语出现的论文id集合
     *
     * @param termId 术语id
     * @param max    最大上限
     * @return 论文id集合
     */
    @Select("select pt.paper_id from paper_term pt, paper_statistics ps " +
            "where pt.term_id=#{termId} and pt.paper_id = ps.paper_id " +
            "order by ps.score desc limit #{max}")
    Collection<Integer> selectPapersOfTermOrderByScore(@Param("termId") Integer termId, @Param("max") Integer max);

    /**
     * 获取指定术语的活跃作者
     *
     * @param termId 术语id
     * @param max    最大上限
     * @return 作者id列表
     */
    @Select("select pa.author_id from paper_statistics ps, paper_authors pa " +
            "where ps.paper_id in (" +
            "   select pt.paper_id from paper_term pt" +
            "       where pt.term_id = #{termId}) " +
            "and ps.paper_id = pa.paper_id " +
            "group by pa.author_id order by avg(ps.score) desc limit #{max}")
    Collection<Integer> selectAuthorsOfTermOrderByActive(@Param("termId") Integer termId, @Param("max") Integer max);

    /**
     * 获取指定术语的活跃机构
     *
     * @param termId 术语id
     * @param max    最大上限
     * @return 机构id列表
     */
    @Select("select aff.id from affiliation aff, author aut, " +
            "   (select pa.author_id, sum(ps.score) as score " +
            "   from paper_statistics ps, paper_authors pa " +
            "   where ps.paper_id in (" +
            "       select pt.paper_id from paper_term pt" +
            "           where pt.term_id = #{termId}" +
            "       ) " +
            "   and ps.paper_id = pa.paper_id " +
            "   group by pa.author_id " +
            "   ) x " +
            "where aut.affiliation_id = aff.id and aut.id = x.author_id " +
            "group by aff.id order by avg(x.score) desc " +
            "limit #{max}")
    Collection<Integer> selectAffiliationsOfTermOrderByActive(@Param("termId") Integer termId, @Param("max") Integer max);

    /**
     * 查找最相关几个术语id
     * 衡量标准：共同出现在文章中的次数
     *
     * @param termId 术语id
     * @param max    数量上限
     * @return 相关术语id集合
     */
    @Select("select pt2.term_id from paper_term pt1, paper_term pt2 " +
            "where pt1.term_id = #{termId} " +
            "   and pt1.term_id != pt2.term_id " +
            "   and pt1.paper_id = pt2.paper_id " +
            "group by pt2.term_id order by count(*) desc " +
            "limit #{max}")
    Collection<Integer> selectRelativeTermsOfTerm(@Param("termId") Integer termId, @Param("max") Integer max);
}
