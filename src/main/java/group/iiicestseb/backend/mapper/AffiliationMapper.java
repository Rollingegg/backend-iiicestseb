package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Affiliation;

public interface AffiliationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Affiliation record);

    int insertSelective(Affiliation record);

    Affiliation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Affiliation record);

    int updateByPrimaryKey(Affiliation record);
}