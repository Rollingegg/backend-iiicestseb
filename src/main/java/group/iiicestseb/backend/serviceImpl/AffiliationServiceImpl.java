package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Affiliation")
@Transactional(rollbackFor = Exception.class)
public class AffiliationServiceImpl implements AffiliationService {
    @Resource
    private AffiliationMapper affiliationMapper;


    @Override
    public AffiliationInfoVO getAffiliationInfo(String name) {
        AffiliationInfoVO affiliationInfoVO = new AffiliationInfoVO();
        //获取机构信息
        Affiliation affiliation = affiliationMapper.selectByName(name);
        System.out.println(affiliation.getId());
        affiliationInfoVO.setId(affiliation.getId());
        affiliationInfoVO.setName(affiliation.getName());
        return affiliationInfoVO;
    }

    @Override
    public Affiliation selectAffiliationById(int id) {
        return affiliationMapper.selectByPrimaryKey(id);
    }
}
