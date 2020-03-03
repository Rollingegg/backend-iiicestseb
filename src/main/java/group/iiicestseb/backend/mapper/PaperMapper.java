package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface PaperMapper {

    /**
     * 通过id删除文献
     *
     * @param id 文献id
     */
    @Delete("delete from paper where id = #{id,jdbcType=INTEGER}")
    void deleteByPrimaryKey(Integer id);

    /**
     * 插入文献
     *
     * @param record 文献id
     */
    @Insert("insert into paper (id, publication_title, publisher_id, " +
            "      conference_id, pdf_link, DOI, " +
            "      paper_title, paper_abstract, reference_count, " +
            "      citation_count, publication_year, start_page, " +
            "      end_page, document_identifier" +
            "      )" +
            "    values (#{id,jdbcType=INTEGER}, #{publicationTitle,jdbcType=VARCHAR}, #{publisherId,jdbcType=INTEGER}, " +
            "      #{conferenceId,jdbcType=INTEGER}, #{pdfLink,jdbcType=VARCHAR}, #{doi,jdbcType=VARCHAR}, " +
            "      #{paperTitle,jdbcType=VARCHAR}, #{paperAbstract,jdbcType=VARCHAR}, #{referenceCount,jdbcType=INTEGER}, " +
            "      #{citationCount,jdbcType=INTEGER}, #{publicationYear,jdbcType=INTEGER}, #{startPage,jdbcType=INTEGER}, " +
            "      #{endPage,jdbcType=INTEGER}, #{documentIdentifier,jdbcType=VARCHAR}" +
            "      ) ")
    @Options(useGeneratedKeys = true)
    void insert(Paper record);

    /**
     * 插入文献列表
     *
     * @param paperList 文献实体列表
     * @return 插入的行数
     */
    int insertPaperList(@Param("paperList") List<Paper> paperList);

    /**
     * 通过id选择文献
     *
     * @param id 文献id
     * @return 文献实体
     */
    @Select("    select * from paper where id = #{id,jdbcType=INTEGER}")
    @ResultMap("PaperResultMap")
    Paper selectByPrimaryKey(Integer id);

    /**
     * 通过id更新文献
     *
     * @param record 文献实体
     */
    @Update("update paper" +
            "    set publication_title = #{publicationTitle,jdbcType=VARCHAR}," +
            "      publisher_id = #{publisherId,jdbcType=INTEGER}," +
            "      conference_id = #{conferenceId,jdbcType=INTEGER}," +
            "      pdf_link = #{pdfLink,jdbcType=VARCHAR}," +
            "      DOI = #{doi,jdbcType=VARCHAR}," +
            "      paper_title = #{paperTitle,jdbcType=VARCHAR}," +
            "      paper_abstract = #{paperAbstract,jdbcType=VARCHAR}," +
            "      reference_count = #{referenceCount,jdbcType=INTEGER}," +
            "      citation_count = #{citationCount,jdbcType=INTEGER}," +
            "      publication_year = #{publicationYear,jdbcType=INTEGER}," +
            "      start_page = #{startPage,jdbcType=INTEGER}," +
            "      end_page = #{endPage,jdbcType=INTEGER}" +
            "    where id = #{id,jdbcType=INTEGER}")
    void updateByPrimaryKey(Paper record);

    /**
     * 通过文献名删除文献
     *
     * @param name 文献名
     */
    @Delete("delete from paper where paper_title = #{name,jdbcType=VARCHAR}")
    void deleteByName(String name);

    /**
     * 通过文献名删除文献
     *
     * @param paper 文献实体
     */
    @Update("update paper" +
            "    set publication_title = #{publicationTitle,jdbcType=VARCHAR}," +
            "      publisher_id = #{publisherId,jdbcType=INTEGER}," +
            "      conference_id = #{conferenceId,jdbcType=INTEGER}," +
            "      pdf_link = #{pdfLink,jdbcType=VARCHAR}," +
            "      DOI = #{doi,jdbcType=VARCHAR}," +
            "      paper_title = #{paperTitle,jdbcType=VARCHAR}," +
            "      paper_abstract = #{paperAbstract,jdbcType=VARCHAR}," +
            "      reference_count = #{referenceCount,jdbcType=INTEGER}," +
            "      citation_count = #{citationCount,jdbcType=INTEGER}," +
            "      publication_year = #{publicationYear,jdbcType=INTEGER}," +
            "      start_page = #{startPage,jdbcType=INTEGER}," +
            "      end_page = #{endPage,jdbcType=INTEGER}," +
            "    paper_title = #{paperTitle,jdbcType=VARCHAR}")
    void updateByName(Paper paper);

    /**
     * 通过文献名查找文献信息
     *
     * @param name 文献名
     * @return 文献实体
     */
    @Select("select * from paper where paper_title = #{name}")
    @ResultMap("PaperResultMap")
    Paper selectByName(String name);

    /**
     * 通过id查找出版社名称
     *
     * @param id 出版社id
     * @return 出版社名称
     */
    @Select("select publisher.name from publisher where id = #{id};")
    String selectPublisherNameById(int id);

    /**
     * 通过出版社名称查找出版社
     *
     * @param name 出版社名
     * @return 出版社实体对象
     */
    @Select("select * from conference where name = #{name}")
    @ResultMap("PublisherResultMap")
    Publisher selectPublisherByName(@Param("name") String name);

    /**
     * 增加出版社列表
     *
     * @param publisherList 出版社实体列表
     * @return 插入的行数
     */
    int insertPublisherList(@Param("publisherList") List<Publisher> publisherList);

    /**
     * 通过id查找会议名称
     *
     * @param id 会议id
     * @return 会议名称
     */
    @Select("select conference.name from conference where id = #{id}")
    String selectConferenceNameById(int id);

    /**
     * 通过会议名查找会议
     *
     * @param name 会议名
     * @return 会议对象
     */
    @Select("select * from conference where name = #{name}")
    @ResultMap("ConferenceResultMap")
    Conference selectConferenceByName(@Param("name") String name);

    /**
     * 增加会议列表
     *
     * @param conferenceList 会议实体列表
     * @return 插入的行数
     */
    int insertConferenceList(@Param("conferenceList") List<Conference> conferenceList);

    /**
     * 通过术语名查找术语
     *
     * @param name 术语名
     * @return 术语对象
     */
    @Select("select * from term where word = #{name}")
    @ResultMap("TermResultMap")
    Term selectTermByName(String name);

    /**
     * 增加术语列表
     *
     * @param termList 术语实体列表
     * @return 插入的行数
     */
    int insertTermList(@Param("termList") List<Term> termList);

    /**
     * 增加文献所含术语列表
     *
     * @param paperTermList 文献所含术语实体列表
     * @return 插入的行数
     */
    int insertPaperTermList(@Param("paperTermList") List<PaperTerm> paperTermList);

    /**
     * 增加文献发布列表
     *
     * @param publishList 文献发布实体列表
     * @return 插入的行数
     */
    int insertPublishList(@Param("publishList") List<Publish> publishList);


//    /**
//     * 适用于查找 DOI、标题、摘要 类型的简单查询
//     * @param type 查询类型 适用于 DOI 标题 摘要
//     * @param keywords
//     * @return 文献列表
//     */
//    @Select("select * from paper" +
//            "where ${type} like '%${keywords}%' order by citation_count DESC")
//    @ResultMap("PaperResultMap")
//    ArrayList<Paper> simpleSearchPaperByType(String type,String keywords);
//
//
//    /**
//     * 适用于查找 全部 类型的简单查询
//     * @param keywords 关键字
//     * @return 文献列表
//     */
//    @Select("select * from paper" +
//            "where paper_tile like '%${keywords}%' or" +
//            "paper_abstract like '%${keywords}%' or" +
//            "DOI like '%${keywords}%'" +
//            "order by citation_count DESC")
//    @ResultMap("PaperResultMap")
//    ArrayList<Paper> simpleSearchPaperAll(String keywords);
}