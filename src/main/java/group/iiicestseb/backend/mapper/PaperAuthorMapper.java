package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.PaperAuthors;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface PaperAuthorMapper extends BaseMapper<PaperAuthors> {

    /**
     * 插入文献作者列表
     *
     * @param paperAuthors 文献作者列表
     */
    void insertList(List<PaperAuthors> paperAuthors);

    /**
     * 根据文献id查找作者列表
     *
     * @param paperId 文献id
     * @return 作者列表
     */
    @Select("select a.id as id, name, first_name, last_name, affiliation_id " +
            "from author a, paper_authors pa where " +
            "pa.paper_id=#{paperId, jdbcType=INTEGER}")
    @ResultType(Author.class)
    List<Author> findAuthorsByPaperId(@Param("paperId") Integer paperId);

    /**
     * 根据作者id查找作者列表
     *
     * @param authorId 作者id
     * @return 文献列表
     */
    @Select("select p.* " +
            "from paper p, paper_authors pa where " +
            "pa.paper_id=#{paperId, jdbcType=INTEGER}")
    @ResultType(Paper.class)
    List<Paper> findPapersByAuthorId(Integer authorId);

}
