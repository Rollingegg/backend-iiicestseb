package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Term;

public interface TermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Term record);

    int insertSelective(Term record);

    Term selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Term record);

    int updateByPrimaryKey(Term record);
}