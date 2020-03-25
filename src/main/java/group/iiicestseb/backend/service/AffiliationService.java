package group.iiicestseb.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import group.iiicestseb.backend.entity.Affiliation;

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

//    /**
//     * 获取机构页面的机构详细信息
//     * @param name 机构名称
//     * @return 机构详细信息
//     */
//    AffiliationInfoVO getAffiliationInfo(String name);
//
//
//    /**
//     * 通过id查找Affiliation
//     * @param id 机构id
//     * @return 机构实体
//     */
//    Affiliation selectAffiliationById(int id);


}
