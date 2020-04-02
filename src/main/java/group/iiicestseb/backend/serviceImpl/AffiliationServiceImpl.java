package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Affiliation")
@Transactional(rollbackFor = Exception.class)
public class AffiliationServiceImpl extends ServiceImpl<AffiliationMapper, Affiliation> implements AffiliationService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private AffiliationMapper affiliationMapper;

    @Override
    public Affiliation findAffiliationByName(String name) {
        return affiliationMapper.selectByName(name);
    }

    @Override
    public void saveAffiliation(Affiliation affiliation) {
        save(affiliation);
    }

    @Override
    public Collection<Affiliation> findAffiliationByIdBatch(Collection<Integer> ids) {
        return this.listByIds(ids);
    }

    @Override
    public AffiliationInfoVO selectBasicInfoByAffiliationId(Integer id) {
        return affiliationMapper.selectBasicInfoById(id);
    }


}
