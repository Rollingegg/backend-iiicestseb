package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Record;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author wph
 */
@Mapper
public interface RecordMapper extends BaseMapper<Record> {
    /**
     * 根据用户id查找用户记录
     * @param userId 用户id
     * @return 用户浏览记录列表
     */
    @Select("select * from record where user_id = #{userId}")
    List<Record> findByUserId(Integer userId);


}
