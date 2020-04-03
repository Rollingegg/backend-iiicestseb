package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.PaperAuthors;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.Collection;
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
     * @return 作者id列表
     */
    @Select("select a.id " +
            "from author a, paper_authors pa where " +
            "pa.paper_id=#{paperId, jdbcType=INTEGER} " +
            "and a.id=pa.author_id")
    List<Integer> findAuthorsByPaperId(@Param("paperId") Integer paperId);

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

    /**
     * 查找论文相关的其他作者，todo：暂时只返回论文原作者，等关系网构建完成再写
     *
     * @param paperId 论文id
     * @param num 最大数量
     * @return 作者id列表
     */
    @Select("select author_id from paper_authors order by author_order limit #{num}")
    List<Integer> selectSimilarAuthorIdsByPaperId(Integer paperId, Integer num);
}
