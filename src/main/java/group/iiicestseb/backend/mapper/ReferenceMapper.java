package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Reference;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface ReferenceMapper extends BaseMapper<Reference> {

    /**
     * 插入文献引用列表
     *
     * @param references 引用列表
     */
    void insertList(@Param("list") List<Reference> references);

    /**
     * 通过IEEE的articleId查找论文的引用列表
     *
     * @param articleId IEEE的articleId
     * @return 引用列表
     */
    @Select("select * " +
            "from reference where " +
            "article_id=#{articleId, jdbcType=INTEGER} " +
            "order by reference_order")
    @ResultType(Reference.class)
    List<Reference> selectByArticleId(Integer articleId);
}
