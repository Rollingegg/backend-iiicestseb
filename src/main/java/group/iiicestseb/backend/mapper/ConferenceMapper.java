package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Conference;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/20
 */
@Mapper
public interface ConferenceMapper extends BaseMapper<Conference> {

    /**
     * 通过名字查找会议
     *
     * @param name 会议名
     * @return 会议
     */
    @Select("select * from conference where name=#{name}")
    @ResultType(Conference.class)
    Conference findByName(@Param("name") String name);
}
