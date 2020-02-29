package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Conference;

public interface ConferenceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Conference record);

    int insertSelective(Conference record);

    Conference selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Conference record);

    int updateByPrimaryKey(Conference record);
}