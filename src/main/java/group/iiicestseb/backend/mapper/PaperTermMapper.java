package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperTerm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface PaperTermMapper extends BaseMapper<PaperTerm> {

    /**
     * 批量插入论文-术语对
     *
     * @param paperTerms 论文-术语对
     */
    void insertList(List<PaperTerm> paperTerms);

    /**
     * 查找相同受控术语数最多的论文id列表
     *
     * @param paperId 论文id
     * @param num
     * @return 列表
     */
    @Select("select pt1.paper_id, count(*) as similar from paper_term pt1, paper_term pt2 " +
            " where pt2.paper_id=#{paper_id, jdbcType=INTEGER} " +
            " and pt1.term_id=pt2.term_id " +
            " and pt1.paper_id!=pt2.paper_id " +
            " group by pt1.paper_id order by similar desc limit #{num}")
    List<Integer> selectSimilarPaperIdsByPaperId(Integer paperId, Integer num);

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
