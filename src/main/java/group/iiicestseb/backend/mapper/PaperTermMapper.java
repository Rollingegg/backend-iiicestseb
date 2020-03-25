package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperTerm;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface PaperTermMapper extends BaseMapper<PaperTerm> {

    /**
     * 批量插入论文-术语对
     * @param paperTerms 论文-术语对
     */
    void insertList(List<PaperTerm> paperTerms);
}
