package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author wph
 */
public interface RecordMapper extends JpaRepository<Record,Integer> {

    /**
     * 根据用户id查找用户记录
     * @param userId 用户id
     * @return 用户浏览记录列表
     */
    public List<Record> findByUserId(Integer userId);
}
