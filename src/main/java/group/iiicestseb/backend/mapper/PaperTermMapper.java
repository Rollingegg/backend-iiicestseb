package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.PaperTerm;

public interface PaperTermMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PaperTerm record);

    int insertSelective(PaperTerm record);

    PaperTerm selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PaperTerm record);

    int updateByPrimaryKey(PaperTerm record);
}