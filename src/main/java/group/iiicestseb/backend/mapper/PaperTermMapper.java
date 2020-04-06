package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperTerm;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Collection;
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
     * @param num     最大上限数量
     * @return 列表
     */
    @Select("select pt1.paper_id from paper_term pt1, paper_term pt2 " +
            " where pt2.paper_id=#{paperId, jdbcType=INTEGER} " +
            " and pt1.term_id=pt2.term_id " +
            " and pt1.paper_id!=pt2.paper_id " +
            " group by pt1.paper_id order by count(*) desc limit #{num}")
    List<Integer> selectSimilarPaperIdsByPaperId(Integer paperId, Integer num);


    /**
     * 获取指定文章的热门术语
     * 热度按照在所有论文中出现的次数
     *
     * @param id  论文id
     * @param max 上限
     * @return 带热度的论文术语
     */
    @Select("select pt1.term_id as id, t.name, count(*) as hot " +
            "from paper_term pt1, " +
            "     term t " +
            "where pt1.term_id in (select term_id " +
            "                      from paper_term pt2 " +
            "                      where pt2.paper_id = #{id}) " +
            "  and pt1.term_id = t.id " +
            "group by t.id order by hot desc ")
    Collection<TermWithHotVO> selectHotTermByPaperId(@Param("id") Integer id, @Param("max") Integer max);

    /**
     * 查找和术语堆相关的论文
     *
     * @param termIds 术语集合
     * @return 论文id集合
     */
    @Select("<script> " +
            "select * from paper_term pt where term_id in " +
            "(null <foreach collection='termIds' item='i' separator='' > " +
            ",#{i}" +
            "</foreach>) " +
            "</script>")
    Collection<PaperTerm> selectByTermIds(@Param("termIds") Collection<Integer> termIds);


}
