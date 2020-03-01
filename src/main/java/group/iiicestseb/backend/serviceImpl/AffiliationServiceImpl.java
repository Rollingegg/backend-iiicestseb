package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.AffiliationVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service
public class AffiliationServiceImpl implements AffiliationService {
    @Resource
    private AffiliationMapper affiliationMapper;
    /**
     * 获取机构页面的机构详细信息
     *
     * @param name 机构名称
     * @return
     */
    @Override
    public AffiliationVO getAffiliationInfo(String name) {
        AffiliationVO affiliationVO = new AffiliationVO();
        //获取机构信息
        Affiliation affiliation = affiliationMapper.selectByName(name);
        affiliationVO.setId(affiliation.getId());
        affiliationVO.setName(affiliation.getName());
        affiliationVO.setSecondary(affiliation.getSecondary());
        //todo 机构页面之后的东西 之后会增加
        return affiliationVO;
    }

}
