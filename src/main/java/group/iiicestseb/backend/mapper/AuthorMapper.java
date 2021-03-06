package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.vo.author.AuthorBasicInfoVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.author.AuthorVertexVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;


/**
 * @author wph
 * @date 2020/2/29
 */
@Mapper
public interface AuthorMapper extends BaseMapper<Author> {


    /**
     * 找出文献的所有作者
     *
     * @param paperId 文献id
     * @return 作者基本信息
     */
    @Select("select au.id,au.name,aff.id as aff_id,aff.name as aff_name " +
            "from paper_authors pa, author au, affiliation aff " +
            "where pa.paper_id = #{paperId} and au.id = pa.author_id and au.affiliation_id = aff.id")
    @Results(id = "AuthorInfoResultMap", value = {
            @Result(column = "id", property = "id", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "name", property = "name", javaType = String.class, jdbcType = JdbcType.VARCHAR),
            @Result(column = "aff_id", property = "affiliationId", javaType = Integer.class, jdbcType = JdbcType.INTEGER),
            @Result(column = "aff_name", property = "affiliationName", javaType = String.class, jdbcType = JdbcType.VARCHAR)
    })
    List<AuthorInfoVO> selectAuthorInfoByPaperId(Integer paperId);


    /**
     * 根据机构名搜索该机构所有作者
     *
     * @param id 机构id
     * @return 作者列表
     */
    @Select("select au.id,au.name,au.first_name,au.last_name " +
            "from author au, affiliation aff " +
            "where aff.id=#{id} and aff.id = au.affiliation_id")
    @ResultType(Author.class)
    List<Author> selectAllAuthorByAffiliationId(Integer id);


    /**
     * 根据名字搜索作者
     *
     * @param name 作者名
     * @return 作者PO
     */
    @Select("select * from author where name=#{name};")
    Author selectByName(@Param("name") String name);

    /**
     * 根据id查找批量作者
     *
     * @param ids id集合
     * @return 作者集合
     */
    @Select("<script>" +
            "select au.id,au.name,aff.id as affiliationId,aff.name as affiliationName " +
            "from author au, affiliation aff " +
            "where au.id in " +
            "   (<foreach collection='list' item='i' separator=',' >" +
            "   #{i}" +
            "   </foreach>) " +
            "and au.affiliation_id = aff.id " +
            "</script>")
    Collection<AuthorInfoVO> selectAuthorInfoByIdBatch(Collection<Integer> ids);


    @Select("select au.id,au.name, aff.id as affiliationId,aff.name as affiliationName, " +
            "count(*) as paperCount, sum(p.citation_count_paper) as citationCount  " +
            "from author au, paper_authors pa, paper p,affiliation aff " +
            "where au.id = #{id} and au.id = pa.author_id and pa.paper_id = p.id and aff.id = au.affiliation_id " +
            "group by author_id")
    @ResultType(AuthorBasicInfoVO.class)
    AuthorBasicInfoVO selectAuthorBasicInfoById(int id);


    /**
     * 获取作者合作伙伴
     *
     * @param id    作者id
     * @param limit 搜索数
     * @return 合作伙伴列表
     */
    @Select("select au.id as id,au.name,aff.id as affiliationId,aff.name as affiliationName  " +
            "from author au, affiliation aff, " +
            "(select  pa2.author_id as aid ,count(*) as coop_times " +
            "from paper_authors pa1,paper_authors pa2 " +
            "where pa1.author_id = #{id} and pa1.paper_id = pa2.paper_id and pa1.author_id <> pa2.author_id " +
            "group by pa2.author_id " +
            "limit #{limit})as x " +
            "where x.aid = au.id and aff.id = au.affiliation_id " +
            "order by x.coop_times " +
            "limit #{limit}" +
            "")
    @ResultType(AuthorInfoVO.class)
    Collection<AuthorInfoVO> selectPartnerById(int id, int limit);


    /**
     * 获取作者合作关系表点vo
     * @param id 作者id
     * @param limit 查询数
     * @return 合作关系点VO
     */
    @Select("select au.id as id,au.name,aff.id as affiliationId,aff.name as affiliationName,x.coop_times as paperCount,au_s.h_index as score  " +
            "from author au, affiliation aff, author_statistics au_s, " +
            "(select  pa2.author_id as aid ,count(*) as coop_times " +
            "from paper_authors pa1,paper_authors pa2 " +
            "where pa1.author_id = #{id} and pa1.paper_id = pa2.paper_id " +
            "group by pa2.author_id " +
            "limit #{limit})as x " +
            "where x.aid = au.id and aff.id = au.affiliation_id and au_s.author_id = au.id " +
            "order by x.coop_times " +
            "limit #{limit}" +
            "")
    @ResultType(AuthorVertexVO.class)
    Collection<AuthorVertexVO> selectGraphPartnerById(int id, int limit);

    /**
     * 获取指定作者同机构作者关系图
     * @param id 作者id
     * @param limit 上限设置
     * @return 作者同机构关系图
     */
    @Select("select au2.id as id,au2.name,aff.id as affiliationId,aff.name as affiliationName,null as paparCount,au_s.h_index as score " +
            "from author au1, author au2, affiliation aff,author_statistics au_s " +
            "where au1.id = #{id} and aff.id = au1.affiliation_id and au2.affiliation_id = aff.id and au2.id = au_s.author_id ")
    @ResultType(AuthorVertexVO.class)
    Collection<AuthorVertexVO> selectGraphAffiliationByPaperId(int id, int limit);
}