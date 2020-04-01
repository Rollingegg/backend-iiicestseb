package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.vo.AffiliationInfoVO;

import java.util.Collection;

/**
 * @author wph
 * @date 2020/03/01
 */

public interface AffiliationService {

    /**
     * 通过机构名查找机构
     *
     * @param name 机构名
     * @return 机构PO
     */
    Affiliation findAffiliationByName(String name);

    /**
     * 保存机构
     * @param affiliation 机构PO
     */
    void saveAffiliation(Affiliation affiliation);

    /**
     * 通过id批量获取作者的机构
     *
     * @param ids id列表
     * @return 机构集合
     */
    Collection<Affiliation> findAffiliationByIdBatch(Collection<Integer> ids);

    /**
     * 根据机构名称查询其基本信息
     * @param id 机构id
     * @return 机构基本信息
     */
    AffiliationInfoVO selectBasicInfoByAffiliationId(Integer id);

    //todo 机构详情页面活跃度


}
