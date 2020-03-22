package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Term;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/21
 */
@Mapper
public interface TermMapper extends BaseMapper<Term> {

    /**
     * 根据术语名查找术语
     *
     * @param name 术语名
     * @return 术语
     */
    //Term findByName(String name);
}
