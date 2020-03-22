package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.PaperAuthors;
import group.iiicestseb.backend.entity.Record;
import org.apache.ibatis.annotations.Mapper;

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
    //public List<Record> findByUserId(Integer userId);
}
