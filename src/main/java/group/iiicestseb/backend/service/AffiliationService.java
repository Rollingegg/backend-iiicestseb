package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.vo.AffiliationVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public AffiliationVO getAffiliationInfo(String name);


}
