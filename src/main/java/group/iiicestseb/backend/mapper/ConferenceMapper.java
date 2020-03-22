package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Conference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/20
 */
@Repository("ConferenceMapper")
public interface ConferenceMapper extends JpaRepository<Conference, Integer> {

    /**
     * 通过名字查找会议
     *
     * @param name 会议名
     * @return 会议
     */
    Conference findByName(String name);
}
