package group.iiicestseb.backend.mapper;


import group.iiicestseb.backend.vo.AuthorWithPublish;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface StatisticsMapper {

    @Insert("insert into record(search_record, browse_record) " +
            "value ('','');" +
            "select last_insert_id()")
    @Options(useGeneratedKeys = true)
    public int insertUserRecord();

    /**
     * 计算并返回术语的热度
     *
     * @param num 需要返回的个数
     * @return 最热门的num个术语
     */
    @Select("select t.id as id, t.word as word, count(*) as hot from term t, paper_term pt" +
            " where t.id = pt.term_id" +
            "  group by t.id order by hot desc limit #{num}")
    @Results(id = "TermsWithHotResultMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "word", property = "word", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "hot", property = "hot", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    List<TermWithHotVO> selectTermsWithHotLimit(@Param("num") Integer num);

    /**
     * 计算并返回发表论文最多的的num个学者和其发表的论文
     *
     * @param num num个学者
     * @return 最热门的num个学者和其发表的论文
     */
    @Select("select aut.id as id, aut.name as name, aff.name as aff_name, count(*) as publish_num" +
            "   from publish p, author aut, affiliation aff" +
            "       where aut.id = p.author_id and aut.affiliation_id = aff.id" +
            "           group by aut.id" +
            "           order by publish_num desc" +
            "           limit #{num}")
    @Results(id = "AuthorWithPublishResultMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "aff_name", property = "affiliationName", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "publish_num", property = "publishNum", javaType = Integer.class, jdbcType = JdbcType.INTEGER)
    })
    List<AuthorWithPublish> selectMaxPublishAuthorLimit(Integer num);
}
