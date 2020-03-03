package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.vo.AffiliationInfoVO;

/**
 * @author wph
 * @date 2020/03/01
 */
public interface AffiliationService {

    /**
     * 获取机构页面的机构详细信息
     * @param name 机构名称
     * @return
     */
    public AffiliationInfoVO getAffiliationInfo(String name);


    /**
     * 通过id查找Affiliation
     * @param id 机构id
     * @return 机构实体
     */
    public Affiliation selectAffiliationById(int id);


}
