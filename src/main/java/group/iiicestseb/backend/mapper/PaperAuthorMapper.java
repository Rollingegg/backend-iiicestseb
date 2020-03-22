package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperAuthors;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface PaperAuthorMapper extends BaseMapper<PaperAuthors> {

}
