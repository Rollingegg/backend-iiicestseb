package group.iiicestseb.backend.mapper;


import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.PaperInfoVO;
import org.apache.ibatis.annotations.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author jh
 * @date 2020/2/22
 */
public interface PaperMapper extends JpaRepository<Paper, Integer> {
//    /**
//     * 适用于 单字段 查找 DOI、标题、摘要 类型的简单查询
//     *
//     * @param type     查询类型 适用于 DOI 标题 摘要 作者 机构
//     * @param keywords 搜索关键字
//     * @return 文献列表
//     */
//    @Query("select " +
//            "p1.id,p1.publication_title,p1.publisher_id,p1.conference_id,p1.pdf_link,p1.DOI, " +
//            "p1.paper_title,p1.paper_abstract,p1.reference_count,p1.citation_count," +
//            "p1.publication_year,p1.start_page,p1.end_page,p1.document_identifier ,publisher.name publisher_name, conference.name conference_name " +
//            "from paper p1,publish pub1,author au1,affiliation af1, conference,publisher " +
//            ",paper_term pt1,term t1 " +
//            "where p1.conference_id=conference.id and publisher.id = p1.publisher_id and " +
//            "p1.id = pub1.paper_id and pub1.author_id = au1.id and au1.affiliation_id = af1.id " +
//            "and p1.id = pt1.paper_id and t1.id= pt1.term_id and" +
//            "${type} like '%${keywords}%'" +
//            "group by p1.id " +
//            "order by citation_count desc limit #{limit}")
//    @ResultMap("PaperInfoVOResultMap")
//    CopyOnWriteArrayList<PaperInfoVO> simpleSearchPaperByType(String type, String keywords,Integer limit);
//
//    List<AuthorInfoVO> selectAuthorInfoById(Integer paperId);
//
//    List<AuthorInfoVO> selectTermById(Integer paperId);
//
//
//
//
//    /**
//     * 适用于模糊字段查找 全部 类型的简单查询
//     *
//     * @param keywords 关键字
//     * @return 文献列表
//     */
//    @Query(value = "select new PaperInfoVO(Paper, ) " +
//            "from Paper, Conference , Author ,",nativeQuery = true,)
//    List<PaperInfoVO> simpleSearchPaperAll(String keywords,Integer limit);
//
//
//    /**
//     * 多字段高级检索
//     *
//     * @param advancedSearchForm 高级检索表单
//     * @return 论文列表
//     */
//    @Select("select " +
//            "p1.id,p1.publication_title,p1.publisher_id,p1.conference_id,p1.pdf_link,p1.DOI, " +
//            "p1.paper_title,p1.paper_abstract,p1.reference_count,p1.citation_count," +
//            "p1.publication_year,p1.start_page,p1.end_page,p1.document_identifier ,publisher.name publisher_name, conference.name conference_name " +
//            "from paper p1,publish pub1,author au1,affiliation af1, conference,publisher " +
//            ",paper_term pt1,term t1 " +
//            "where (p1.conference_id=conference.id and publisher.id = p1.publisher_id and " +
//            "p1.id = pub1.paper_id and pub1.author_id = au1.id and au1.affiliation_id = af1.id " +
//            "and p1.id = pt1.paper_id and t1.id= pt1.term_id and" +
//            "(p1.paper_title like '%${advancedSearchForm.paperTitleKeyword}%' OR #{advancedSearchForm.paperTitleKeyword,jdbcType=VARCHAR} IS NULL) and" +
//            "(p1.paper_abstract like '%${advancedSearchForm.paperAbstractKeyword}%'  OR #{advancedSearchForm.paperAbstractKeyword,jdbcType=VARCHAR} IS NULL) and " +
//            "(p1.DOI like '%${advancedSearchForm.doiKeyword}%'  OR #{advancedSearchForm.doiKeyword,jdbcType=VARCHAR} IS NULL) and " +
//            "(au1.name like '%${advancedSearchForm.authorKeyword}%'  OR #{advancedSearchForm.authorKeyword,jdbcType=VARCHAR} IS NULL) and" +
//            "(af1.name like '%${advancedSearchForm.affiliationKeyword}%'  OR #{advancedSearchForm.affiliationKeyword,jdbcType=VARCHAR} IS NULL) and" +
//            "(t1.word like '%${advancedSearchForm.termKeyword}%'  OR #{advancedSearchForm.termKeyword,jdbcType=VARCHAR} IS NULL))  " +
//            "group by p1.id " +
//            "order by citation_count desc limit #{limit,jdbcType=INTEGER}")
//    @ResultMap("PaperInfoVOResultMap")
//    CopyOnWriteArrayList<PaperInfoVO> advancedSearch(AdvancedSearchForm advancedSearchForm,
//                                                     Integer limit);
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