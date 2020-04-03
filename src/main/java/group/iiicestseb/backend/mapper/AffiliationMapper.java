package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;

/**
 * @author wph
 * @date 2020/2/29
 */
@Mapper
public interface AffiliationMapper extends BaseMapper<Affiliation> {

    /**
     * 通过机构名查找机构
     *
     * @param name 机构名
     * @return 机构
     */
    @Select("select * from affiliation where name = #{name}")
    @ResultType(Affiliation.class)
    Affiliation selectByName(String name);


    /**
     * 根据机构id查询其基本信息
     * @param id 机构id
     * @return 机构基本信息
     */
    @Select("select x.id, x.name,count(*) as authorNum, sum(each_au_paper_num) as paperNum " +
            "from " +
            "(select aff.id, aff.name , au.id as au_id,count(*) as each_au_paper_num " +
            "from affiliation aff, author au,paper_authors pa " +
            "where aff.id = au.affiliation_id and aff.id = #{id} " +
            "and pa.author_id = au.id " +
            "group by au_id) as x " +
            "group by x.id"
    )
    @ResultType(AffiliationInfoVO.class)
    AffiliationInfoVO selectBasicInfoById(Integer id);


}