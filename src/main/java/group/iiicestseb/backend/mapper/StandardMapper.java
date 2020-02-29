package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.TermStandard;

public interface StandardMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TermStandard record);

    int insertSelective(TermStandard record);

    TermStandard selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TermStandard record);

    int updateByPrimaryKey(TermStandard record);
}