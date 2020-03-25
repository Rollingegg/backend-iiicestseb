package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperAuthors;
import org.apache.ibatis.annotations.Mapper;
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
     * @param paperAuthors 文献作者列表
     */
    void insertList(List<PaperAuthors> paperAuthors);
}
