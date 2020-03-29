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
     * @param num 最大上限数量
     * @return 列表
     */
    @Select("select pt1.paper_id from paper_term pt1, paper_term pt2 " +
            " where pt2.paper_id=#{paperId, jdbcType=INTEGER} " +
            " and pt1.term_id=pt2.term_id " +
            " and pt1.paper_id!=pt2.paper_id " +
            " group by pt1.paper_id order by count(*) desc limit #{num}")
    List<Integer> selectSimilarPaperIdsByPaperId(Integer paperId, Integer num);


}
