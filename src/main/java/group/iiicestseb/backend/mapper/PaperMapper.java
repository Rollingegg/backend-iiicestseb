package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import org.apache.ibatis.annotations.*;

import java.util.concurrent.CopyOnWriteArrayList;


/**
 * @author jh
 * @date 2020/2/22
 */
public interface PaperMapper {

    /**
     * 通过id删除文献
     * @param id 文献id
     */
    @Delete("delete from paper where id = #{id,jdbcType=INTEGER}")
    void deleteByPrimaryKey(Integer id);

    /**
     * 插入文献
     * @param record 文献id
     */
    @Insert("insert into paper (id, publication_title, publisher_id, " +
            "      conference_id, pdf_link, DOI, " +
            "      paper_title, paper_abstract, reference_count, " +
            "      citation_count, publication_year, start_page, " +
            "      end_page, author_keywords, document_identifier" +
            "      )" +
            "    values (#{id,jdbcType=INTEGER}, #{publicationTitle,jdbcType=VARCHAR}, #{publisherId,jdbcType=INTEGER}, " +
            "      #{conferenceId,jdbcType=INTEGER}, #{pdfLink,jdbcType=VARCHAR}, #{doi,jdbcType=VARCHAR}, " +
            "      #{paperTitle,jdbcType=VARCHAR}, #{paperAbstract,jdbcType=VARCHAR}, #{referenceCount,jdbcType=INTEGER}, " +
            "      #{citationCount,jdbcType=INTEGER}, #{publicationYear,jdbcType=INTEGER}, #{startPage,jdbcType=INTEGER}, " +
            "      #{endPage,jdbcType=INTEGER}, #{authorKeywords,jdbcType=VARCHAR}, #{documentIdentifer,jdbcType=VARCHAR}" +
            "      ) ")
    @Options(useGeneratedKeys = true)
    void insert(Paper record);

    /**
     * 通过id选择文献
     * @param id 文献id
     * @return 文献实体
     */
    @Select("    select * from paper where id = #{id,jdbcType=INTEGER}")
    @ResultMap("PaperResultMap")
    Paper selectByPrimaryKey(Integer id);


    /**
     * 通过id更新文献
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
            "      end_page = #{endPage,jdbcType=INTEGER}," +
            "      author_keywords = #{authorKeywords,jdbcType=VARCHAR}" +
            "    where id = #{id,jdbcType=INTEGER}")
    void updateByPrimaryKey(Paper record);

    /**
     * 通过文献名删除文献
     * @param name 文献名
     */
    @Delete("delete from paper where paper_title = #{name,jdbcType=VARCHAR}")
    void deleteByName(String name);

    /**
     * 通过文献名删除文献
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
            "      author_keywords = #{authorKeywords,jdbcType=VARCHAR}" +
            "    where paper_title = #{paperTitle,jdbcType=VARCHAR}")
    void updateByName(Paper paper);

    /**
     * 通过文献名查找文献信息
     * @param name 文献名
     * @return 文献实体
     */
    @Select("select * from paper where paper_title = #{name}")
    @ResultMap("PaperResultMap")
    Paper selectByName(String name);

    /**
     * 通过id查找出版社名称
     * @param id 出版社id
     * @return 出版社名称
     */
    @Select("select publisher.name from publisher where id = #{id};")
    String selectPublisherNameById(int id);

    /**
     * 通过id查找会议名称
     * @param id 会议id
     * @return 会议名称
     */
    @Select("select conference.name from conference where id = #{id}")
    String selectConferenceNameById(int id);

    /**
     * 适用于 单字段 查找 DOI、标题、摘要 类型的简单查询
     * @param type 查询类型 适用于 DOI 标题 摘要 作者 机构
     * @param keywords 搜索关键字
     * @return 文献列表
     */
    @Select("select distinct paper.* from paper,publish,author,affiliation " +
            "where ${type} like '%${keywords}%' and " +
            "paper.id = publish.paper_id and " +
            "publish.author_id = author.id and " +
            "author.affiliation_id = affiliation.id")
    @ResultMap("PaperResultMap")
    CopyOnWriteArrayList<Paper> simpleSearchPaperByType(String type, String keywords);


    /**
     * 适用于模糊字段查找 全部 类型的简单查询
     * @param keywords 关键字
     * @return 文献列表
     */
    @Select("select distinct paper.* from paper,publish,author,affiliation " +
            "where paper.id = publish.paper_id and " +
            "publish.author_id = author.id and " +
            "author.affiliation_id = affiliation.id and " +
            "(author.name like '%${keywords}%' or " +
            "affiliation.name like '%${keywords}%' or " +
            "paper.DOI like '%${keywords}%' or " +
            "paper.paper_abstract like '%${keywords}%' or " +
            "paper.paper_title like '%${keywords}%')" +
            "order by citation_count DESC ")
    @ResultMap("PaperResultMap")
    CopyOnWriteArrayList<Paper> simpleSearchPaperAll(String keywords);


    /**
     * 多字段高级检索
     * @param advancedSearchForm 高级检索表单
     * @return 论文列表
     */
    @Select("select distinct paper.* from paper,publish,author,affiliation " +
            "where paper.id = publish.paper_id and " +
            "publish.author_id = author.id and " +
            "author.affiliation_id = affiliation.id and " +
            "(paper.paper_title like #{paperTitleKeyword}  OR #{paperTitleKeyword} IS NULL) and" +
            "(paper.paper_abstract like #{paperAbstractKeyword}  OR #{paperAbstractKeyword} IS NULL) and " +
            "(paper.DOI like #{doiKeyword}  OR #{doiKeyword} IS NULL) and " +
            "(paper.paper_abstract like #{paperAbstractKeyword}  OR #{paperAbstractKeyword} IS NULL)" +
            "order by citation_count desc")
    @ResultMap("PaperResultMap")
    CopyOnWriteArrayList<Paper> advancedSearch(AdvancedSearchForm advancedSearchForm);
}