package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Reference;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
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
}
