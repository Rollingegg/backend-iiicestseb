package group.iiicestseb.backend.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.vo.SearchResultVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.type.JdbcType;

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


            "select p.id,p.title,p.paper_abstract,p.pdf_url,p.citation_count_paper,p.id,p.id  " +
            "from paper p " +
            //高级搜索
            "<if test='type==\"advanced\"'> " +
            ",((select pa.paper_id as key_id " +
            "from author a, affiliation aff, paper_authors pa " +
            "where a.id = pa.author_id and a.affiliation_id = aff.id  " +
            "and ((aff.name is null or aff.name like '%${affiliationKeyword}%') and (a.name is null or a.name like '%${authorKeyword}%'))) as x " +
            "inner join " +
            "(select pt.paper_id as key_id " +
            "from paper_term pt, term t " +
            "where t.id = pt.term_id " +
            "and (t.name is null or t.name like '%${termKeyword}%')) as y on(x.key_id = y.key_id) ) " +
            "where p.id = x.key_id " +
            "and (p.title is null or p.title like '%${titleKeyword}%') " +
            "and (p.paper_abstract is null or p.paper_abstract like '%${paperAbstractKeyword}%')" +
            "and (p.doi is null or p.doi like '%${doiKeyword}%')" +
            "</if>" +
            //all搜索
            "<if test='type==\"all\"'> " +
            ",((select pa.paper_id as key_id " +
            "from author a, affiliation aff, paper_authors pa " +
            "where a.id = pa.author_id and a.affiliation_id = aff.id  " +
            "and ((aff.name is not null and aff.name like '%${allKeyword}%') or (a.name like '%${allKeyword}%' and a.name is not null))) " +
            "union " +
            "(select pt.paper_id as key_id " +
            "from paper_term pt, term t " +
            "where t.id = pt.term_id " +
            "and (t.name is not null and t.name like '%${allKeyword}%'))) as x " +
            "where p.id = x.key_id  " +
            "or (p.title like '%${allKeyword}%' and p.title is not null)" +
            "or (p.paper_abstract like '%${allKeyword}%' and p.paper_abstract is not null)" +
            "or (p.doi like '%${allKeyword}%' and p.doi is not null)" +
            "</if>" +
            //简单搜索术语， 连接文献表
            "<if test='type==\"affiliation\"'> " +
            ", paper_term pt, term t " +
            "where p.id = pt.paper_id and t.id = pt.term_id " +
            "and t.name like '%${termKeyword}%'" +
            "</if>" +
            //简单搜索机构， 连接作者表、机构表
            "<if test='type==\"affiliation\"'> " +
            ", author a, paper_authors pa,affiliation aff " +
            "where p.id = pa.paper_id and a.id = pa.author_id and a.affiliation_id = aff.id " +
            "and aff.name like '%${affiliationKeyword}%'" +
            "</if>" +
            //简单搜索搜索作者 只连接作者表
            "<if test='type==\"author\"'> " +
            ", author a, paper_authors pa " +
            "where p.id = pa.paper_id and a.id = pa.author_id  " +
            "and a.name like '%${authorKeyword}%'" +
            "</if>" +
            //简单搜索 论文的三个信息 无需提前链接
            "<if test='type==\"doi\" or  type==\"title\" or type==\"paperAbstract\"'> " +
            "where 1=1 " +
            "<if test='type==\"doi\"'> " +
            " and p.doi like '%${doiKeyword}%'" +
            "</if>" +
            "<if test='type==\"titleKeyword\"'> " +
            " and p.title like '%${titleKeyword}%'" +
            "</if>" +
            "<if test='type==\"paperAbstract\"'> " +
            " and p.paper_abstract like '%${paperAbstractKeyword}%'" +
            "</if>" +
            "</if> " +
            "group by p.id " +
            "order by p.citation_count_paper desc " +
            "limit #{page},#{limit}" +
            "</script>")
    @Results(id = "SearchResultVOResultMap",value = {
            @Result(column = "id",property = "id",jdbcType = JdbcType.INTEGER),
            @Result(column = "title",property = "title",jdbcType = JdbcType.VARCHAR),
            @Result(column = "paper_abstract",property = "paperAbstract",jdbcType = JdbcType.VARCHAR),
            @Result(column = "pdf_url",property = "pdfUrl",jdbcType = JdbcType.VARCHAR),
            @Result(column = "citation_count_paper",property = "citationCountPaper",jdbcType = JdbcType.INTEGER),
            @Result(column = "id",property = "authorList",many = @Many(select = "group.iiicestseb.backend.mapper.AuthorMapper.selectAuthorInfoByPaperId",fetchType = FetchType.EAGER) ),
            @Result(column = "id",property = "termsList",many = @Many(select = "group.iiicestseb.backend.mapper.TermMapper.selectByPaperId",fetchType = FetchType.EAGER) )}
    )
    List<SearchResultVO > advancedSearch(AdvancedSearchForm advancedSearchForm);


//    /**
//     * 通过IEEE的ID查找论文
//     *
//     * @param articleId IEEE制定的id
//     * @return 文献
//     */
//    Paper findByArticleId(Integer articleId);

//    /**
//     * 通过id删除文献
//     *
//     * @param id 文献id
//     * @return 影响行数
//     */
//    @Delete("delete from paper where id = #{id,jdbcType=INTEGER}")
//    int deleteByPrimaryKey(Integer id);
//
//    /**
//     * 插入文献
//     *
//     * @param record 文献id
//     * @return 插入的id
//     */
//    @Insert("insert into paper ( publication_title, publisher_id, " +
//            "      conference_id, pdf_link, DOI, " +
//            "      paper_title, paper_abstract, reference_count, " +
//            "      citation_count, publication_year, start_page, " +
//            "      end_page, document_identifier" +
//            "      )" +
//            "    values ( #{publicationTitle,jdbcType=VARCHAR}, #{publisherId,jdbcType=INTEGER}, " +
//            "      #{conferenceId,jdbcType=INTEGER}, #{pdfLink,jdbcType=VARCHAR}, #{doi,jdbcType=VARCHAR}, " +
//            "      #{paperTitle,jdbcType=VARCHAR}, #{paperAbstract,jdbcType=VARCHAR}, #{referenceCount,jdbcType=INTEGER}, " +
//            "      #{citationCount,jdbcType=INTEGER}, #{publicationYear,jdbcType=TIMESTAMP}, #{startPage,jdbcType=VARCHAR}, " +
//            "      #{endPage,jdbcType=VARCHAR}, #{documentIdentifier,jdbcType=VARCHAR}" +
//            "      ) ;")
//    @Options(useGeneratedKeys = true,keyProperty = "id")
//    int insert(Paper record);
//
//    /**
//     * 插入文献列表
//     *
//     * @param paperList 文献实体列表
//     * @return 插入的行数
//     */
//    int insertPaperList(@Param("paperList") List<Paper> paperList);
//
//    /**
//     * 通过id选择文献
//     *
//     * @param id 文献id
//     * @return 文献实体
//     */
//    @Select("    select * from paper where id = #{id,jdbcType=INTEGER}")
//    @ResultMap("PaperResultMap")
//    Paper selectByPrimaryKey(Integer id);
//
//    /**
//     * 通过id更新文献
//     *
//     * @param record 文献实体
//     * @return 影响行数
//     */
//    @Update("update paper" +
//            "    set publication_title = #{publicationTitle,jdbcType=VARCHAR}," +
//            "      publisher_id = #{publisherId,jdbcType=INTEGER}," +
//            "      conference_id = #{conferenceId,jdbcType=INTEGER}," +
//            "      pdf_link = #{pdfLink,jdbcType=VARCHAR}," +
//            "      DOI = #{doi,jdbcType=VARCHAR}," +
//            "      paper_title = #{paperTitle,jdbcType=VARCHAR}," +
//            "      paper_abstract = #{paperAbstract,jdbcType=VARCHAR}," +
//            "      reference_count = #{referenceCount,jdbcType=INTEGER}," +
//            "      citation_count = #{citationCount,jdbcType=INTEGER}," +
//            "      publication_year = #{publicationYear,jdbcType=TIMESTAMP}," +
//            "      start_page = #{startPage,jdbcType=VARCHAR}," +
//            "      end_page = #{endPage,jdbcType=VARCHAR}" +
//            "    where id = #{id,jdbcType=INTEGER}")
//    int updateByPrimaryKey(Paper record);
//
//    /**
//     * 通过文献名删除文献
//     *
//     * @param name 文献名
//     * @return 影响行数
//     */
//    @Delete("delete from paper where paper_title = #{name,jdbcType=VARCHAR}")
//    int deleteByName(String name);
//
//    /**
//     * 通过文献名更新文献
//     *
//     * @param paper 文献实体
//     * @return 影响行数
//     */
//    @Update("update paper" +
//            "    set publication_title = #{publicationTitle,jdbcType=VARCHAR}," +
//            "      publisher_id = #{publisherId,jdbcType=INTEGER}," +
//            "      conference_id = #{conferenceId,jdbcType=INTEGER}," +
//            "      pdf_link = #{pdfLink,jdbcType=VARCHAR}," +
//            "      DOI = #{doi,jdbcType=VARCHAR}," +
//            "      paper_title = #{paperTitle,jdbcType=VARCHAR}," +
//            "      paper_abstract = #{paperAbstract,jdbcType=VARCHAR}," +
//            "      reference_count = #{referenceCount,jdbcType=INTEGER}," +
//            "      citation_count = #{citationCount,jdbcType=INTEGER}," +
//            "      publication_year = #{publicationYear,jdbcType=TIMESTAMP}," +
//            "      start_page = #{startPage,jdbcType=VARCHAR}," +
//            "      end_page = #{endPage,jdbcType=VARCHAR}" +
//            "    where paper_title = #{paperTitle,jdbcType=VARCHAR}")
//    int updateByName(Paper paper);
//
//
//    /**
//     * 通过文献名查找文献信息 TODO: 应该返回一个列表，因为有同名文献
//     *
//     * @param name 文献名
//     * @return 文献实体
//     */
//    @Select("select * from paper where paper_title = #{name}")
//    @ResultMap("PaperResultMap")
//    Paper selectByName(String name);
//
//    /**
//     * 通过文献名和文献发布年份查找文献信息
//     *
//     * @param name 文献名
//     * @param year 发布年份
//     * @return 文献实体
//     */
//    @Select("select * from paper where paper_title = #{name} and publication_year = #{year}")
//    @ResultMap("PaperResultMap")
//    Paper selectByNameAndYear(String name, LocalDateTime year);
//
//    /**
//     * 通过id查找出版社名称
//     *
//     * @param id 出版社id
//     * @return 出版社名称实体
//     */
//    @Select("select * from publisher where id = #{id};")
//    Publisher selectPublisherById(int id);
//
//    /**
//     * 通过出版社名称查找出版社
//     *
//     * @param name 出版社名
//     * @return 出版社实体对象
//     */
//    @Select("select * from publisher where name = #{name}")
//    @ResultMap("PublisherResultMap")
//    Publisher selectPublisherByName(@Param("name") String name);
//
//    /**
//     * 增加出版社列表
//     *
//     * @param publisherList 出版社实体列表
//     * @return 插入的行数
//     */
//    int insertPublisherList(@Param("publisherList") List<Publisher> publisherList);
//
//
//
//    /**
//     * 通过id查找会议
//     *
//     * @param id 会议id
//     * @return 会议实体
//     */
//    @Select("select * from conference where id = #{id}")
//    @ResultMap("ConferenceResultMap")
//    Conference selectConferenceById(int id);
//
//    /**
//     * 通过会议名查找会议
//     *
//     * @param name 会议名
//     * @return 会议对象
//     */
//    @Select("select * from conference where name = #{name}")
//    @ResultMap("ConferenceResultMap")
//    Conference selectConferenceByName(@Param("name") String name);
//
//    /**
//     * 增加会议列表
//     *
//     * @param conferenceList 会议实体列表
//     * @return 插入的行数
//     */
//    int insertConferenceList(@Param("conferenceList") List<Conference> conferenceList);
//
//    /**
//     * 通过术语名查找术语
//     *
//     * @param name 术语名
//     * @return 术语对象
//     */
//    @Select("select * from term where word = #{name}")
//    @ResultMap("TermResultMap")
//    Term selectTermByName(String name);
//
//    /**
//     * 增加术语列表
//     *
//     * @param termList 术语实体列表
//     * @return 插入的行数
//     */
//    int insertTermList(@Param("termList") List<Term> termList);
//
//    /**
//     * 增加文献所含术语列表
//     *
//     * @param paperTermList 文献所含术语实体列表
//     * @return 插入的行数
//     */
//    int insertPaperTermList(@Param("paperTermList") List<PaperTerm> paperTermList);
//
//    /**
//     * 增加文献发布列表
//     *
//     * @param publishList 文献发布实体列表
//     * @return 插入的行数
//     */
//    int insertPublishList(@Param("publishList") List<Publish> publishList);
//


}