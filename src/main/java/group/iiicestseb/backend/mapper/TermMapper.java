package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Term;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface TermMapper extends BaseMapper<Term> {

    /**
     * 查询文献对应的所有术语
     * @param paperId 文献id
     * @return 文献所有的术语列表
     */
    @Select("select t.id,t.name " +
            "from paper_term pt, term t " +
            "where pt.paper_id = #{paperId} and t.id = pt.term_id")
    @Results(id = "TermResultMap",value = {
            @Result(column = "id",property = "id",javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "name",property = "name",javaType = String.class,jdbcType = JdbcType.VARCHAR),
    })
    List<Term> selectByPaperId(Integer paperId);

    /**
     * 根据术语名查找术语
     *
     * @param name 术语名
     * @return 术语
     */
    @Select("select * from term where name=#{name}")
    @ResultType(Term.class)
    Term selectByName(@Param("name") String name);

    /**
     * 批量存储术语
     *
     * @param termList 术语列表
     */
    void insertTermList(List<Term> termList);
}
