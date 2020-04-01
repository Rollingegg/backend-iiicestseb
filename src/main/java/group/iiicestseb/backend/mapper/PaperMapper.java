package group.iiicestseb.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.vo.paper.SearchResultVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

import java.util.Collection;
import java.util.List;


/**
 * @author jh
 * @date 2020/2/22
 */
@Mapper
public interface PaperMapper extends BaseMapper<Paper> {


    @Select("select * from paper where article_id=#{articleId}")
    @ResultType(Paper.class)
    Paper selectByArticleId(@Param("articleId") Integer articleId);

    /**
     * 多字段高级检索
     *
     * @param advancedSearchForm 高级检索表单
     * @return 论文列表
     */
    @Select("<script>" +
            "select p.id,p.title,p.paper_abstract,p.pdf_url,p.chron_date,p.citation_count_paper,p.id,p.id  " +
            "from paper p " +
            //高级搜索
            "<if test='type==\"advanced\"'> " +
            ",((select pa.paper_id as key_id " +
            "from author a, affiliation aff, paper_authors pa " +
            "where a.id = pa.author_id and a.affiliation_id = aff.id  " +
            "and ((#{affiliationKeyword} is null or  LOCATE(#{affiliationKeyword}, aff.name)>0) and (#{authorKeyword} is null or LOCATE(#{authorKeyword}, a.name)>0))) as x " +
            "inner join " +
            "(select pt.paper_id as key_id " +
            "from paper_term pt, term t " +
            "where t.id = pt.term_id " +
            "and (#{termKeyword} is null or LOCATE(#{termKeyword}, t.name)>0)) as y on(x.key_id = y.key_id) ) " +
            "where p.id = x.key_id " +
            "and (#{titleKeyword} is null or LOCATE(#{titleKeyword}, p.title)>0) " +
            "and (#{paperAbstractKeyword} is null or LOCATE(#{paperAbstractKeyword}, p.paper_abstract)>0)" +
            "and (#{doiKeyword} is null or LOCATE(#{doiKeyword}, p.doi)>0)" +
            "</if>" +
            //all搜索
            "<if test='type==\"all\"'> " +
            ",((select pa.paper_id as key_id " +
            "from author a, affiliation aff, paper_authors pa " +
            "where a.id = pa.author_id and a.affiliation_id = aff.id  " +
            "and (( LOCATE(#{allKeyword}, aff.name)>0) or ( LOCATE(#{allKeyword}, a.name)>0))) " +
            "union " +
            "(select pt.paper_id as key_id " +
            "from paper_term pt, term t " +
            "where t.id = pt.term_id " +
            "and (LOCATE(#{allKeyword}, t.name)>0))) as x " +
            "where p.id = x.key_id  " +
            "or (LOCATE(#{allKeyword}, p.title)>0)" +
            "or (LOCATE(#{allKeyword}, p.paper_abstract)>0)" +
            "or (LOCATE(#{allKeyword}, p.doi)>0)" +
            "</if>" +
            //简单搜索术语， 连接文献表
            "<if test='type==\"term\"'> " +
            ", paper_term pt, term t " +
            "where p.id = pt.paper_id and t.id = pt.term_id " +
            "and LOCATE(#{termKeyword}, t.name)>0 " +
            "</if>" +
            //简单搜索机构， 连接作者表、机构表
            "<if test='type==\"affiliation_name\"'> " +
            ", author a, paper_authors pa,affiliation aff " +
            "where p.id = pa.paper_id and a.id = pa.author_id and a.affiliation_id = aff.id " +
            "and LOCATE(#{affiliationKeyword}, aff.name)>0" +
            "</if>" +
            //简单搜索搜索作者 只连接作者表
            "<if test='type==\"author_name\"'> " +
            ", author a, paper_authors pa " +
            "where p.id = pa.paper_id and a.id = pa.author_id  " +
            "and LOCATE(#{authorKeyword}, a.name)>0" +
            "</if>" +
            //简单搜索 论文的三个信息 无需提前链接
            "<if test='type==\"doi\" or  type==\"title\" or type==\"paper_abstract\"'> " +
            "where 1=1 " +
            "<if test='type==\"doi\"'> " +
            " and LOCATE(#{doiKeyword}, p.doi)>0" +
            "</if>" +
            "<if test='type==\"titleKeyword\"'> " +
            " and LOCATE(#{titleKeyword}, p.title)>0" +
            "</if>" +
            "<if test='type==\"paperAbstract\"'> " +
            " and LOCATE(#{paperAbstractKeyword}, p.paper_abstract)>0)" +
            "</if>" +
            "</if> " +
            //年份
            " <![CDATA[ and p.chron_date >= str_to_date(#{chronDateMinKeyword},'%Y-%m-%d') and p.chron_date <= str_to_date(#{chronDateMaxKeyword},'%Y-%m-%d') ]]>  " +
            "group by p.id " +
            "order by p.citation_count_paper desc " +
            "limit #{page},#{limit}" +
            "</script>")
    @Results(id = "SearchResultVOResultMap",value = {
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER),
            @Result(column = "title",property = "title",jdbcType = JdbcType.VARCHAR),
            @Result(column = "paper_abstract",property = "paperAbstract",jdbcType = JdbcType.VARCHAR),
            @Result(column = "pdf_url",property = "pdfUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column = "chron_date",property = "chronDate",jdbcType = JdbcType.VARCHAR),
            @Result(column = "citation_count_paper",property = "citationCountPaper",jdbcType = JdbcType.INTEGER),
            @Result(column = "id",property = "authorList",many = @Many(select = "group.iiicestseb.backend.mapper.AuthorMapper.selectAuthorInfoByPaperId",fetchType = FetchType.EAGER) ),
            @Result(column = "id",property = "termsList",many = @Many(select = "group.iiicestseb.backend.mapper.TermMapper.selectByPaperId",fetchType = FetchType.EAGER) )}
    )
    List<SearchResultVO > advancedSearch(AdvancedSearchForm advancedSearchForm);


    /**
     * 查找机构最近文章
     * @param id 机构id
     * @param limit 搜索数
     * @return 机构最近文章列表
     */
    @Select("select p.* " +
            "from paper p,author au, affiliation aff,paper_authors pa " +
            "where aff.id=#{id} and aff.id = au.affiliation_id and au.id = pa.author_id and p.id = pa.paper_id " +
            "order by chron_date desc " +
            "limit #{limit}")
    @ResultType(Paper.class)
    List<Paper> selectRecentPaperByAffiliationId(Integer id,Integer limit);


    /**
     * 查找机构所有文章
     * @param id 机构id
     * @return 机构文章列表
     */
    @Select("select p.id,p.title,p.paper_abstract,p.pdf_url,p.chron_date,p.citation_count_paper,p.id,p.id  " +
            "from paper p, paper_authors pa, author au, affiliation aff " +
            "where aff.id = #{id} and pa.paper_id = p.id and pa.author_id = au.id and aff.id = au.affiliation_id " +
            "order by p.citation_count_paper desc")
    Collection<SearchResultVO> selectAllPaperByAffiliationId(Integer id);


    /**
     * 查找作者的所有文献
     * @param id 作者id
     * @return 作者所有文献
     */
    @Select("select p.id,p.title,p.paper_abstract,p.pdf_url,p.chron_date,p.citation_count_paper,p.id,p.id  " +
            "from paper p, paper_authors pa, author au " +
            "where au.id = #{id} and pa.paper_id = p.id and pa.author_id = au.id " +
            "order by p.citation_count_paper desc")
    Collection<SearchResultVO> selectAllPaperByAuthorId(Integer id);

    /**
     * 查找作者最近发表文献
     * @param id 作者id
     * @param limit 搜索数
     * @return 作者最近发表文献列表
     */
    @Select("select p.* " +
            "from paper p,author au,paper_authors pa " +
            "where au.id=#{id} and au.id = pa.author_id and p.id = pa.paper_id " +
            "order by chron_date desc " +
            "limit #{limit}")
    @ResultType(Paper.class)
    List<Paper> selectRecentPaperByAuthorId(Integer id,Integer limit);
}