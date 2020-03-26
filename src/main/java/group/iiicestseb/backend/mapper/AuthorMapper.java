package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

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
     * 根据名字搜索作者
     *
     * @param name 作者名
     * @return 作者PO
     */
    @Select("select * from author where name=#{name};")
    Author selectByName(@Param("name") String name);

//    /**
//     * 根据学者名查找学者
//     *
//     * @param name 学者名
//     * @return 学者
//     */
    //Author findByName(String name);

//    /**
//     * 通过id删除作者
//     *
//     * @param id 作者id
//     * @return 修改行数
//     * @author wph
//     */
//    @Delete("delete from author where id = #{id,jdbcType=INTEGER}")
//    int deleteByPrimaryKey(Integer id);
//
//    /**
//     * 增加作者
//     *
//     * @param record 作者实体
//     * @return 新增作者id
//     */
//    @Insert("insert into author (name, affiliation_id) values (#{name,jdbcType=VARCHAR}, #{affiliationId,jdbcType=INTEGER});")
//    @Options(useGeneratedKeys = true,keyProperty = "id")
//    int insert(Author record);
//
//    /**
//     * 增加作者列表
//     *
//     * @param authorList 作者实体列表
//     * @return 插入的行
//     */
//    int insertAuthorList(@Param("authorList") List<Author> authorList);
//
//    /**
//     * 通过id搜寻作者
//     *
//     * @param id 作者id
//     * @return 作者实体
//     */
//    @Select("select * from author where id = #{id,jdbcType=INTEGER}")
//    @ResultMap("AuthorResultMap")
//    Author selectByPrimaryKey(Integer id);
//
//    /**
//     * 更新作者信息
//     *
//     * @param record 作者信息实体
//     * @return 修改行数
//     */
//    @Update("update author set name = #{name,jdbcType=VARCHAR}, affiliation_id = #{affiliationId,jdbcType=INTEGER} where id = #{id,jdbcType=INTEGER}")
//    int updateByPrimaryKey(Author record);
//
//    /**
//     * 根据作者名字查找作者信息
//     *
//     * @param name 作者名
//     * @return 作者实体
//     */
//    @Select("select * from author where name = #{name,jdbcType=VARCHAR}")
//    @ResultMap("AuthorResultMap")
//    Author selectByName(String name);
//
//    /**
//     * 查询文献的所有作者
//     *
//     * @param id 文献id
//     * @return 文献作者列表
//     */
//    @Select("select author.name " +
//            "from author,paper,publish " +
//            "where paper.id = publish.paper_id and " +
//            "publish.author_id = author.id and " +
//            "paper.id = #{id,jdbcType=INTEGER}")
//    CopyOnWriteArrayList<String> getAuthorByPaperId(int id);
}