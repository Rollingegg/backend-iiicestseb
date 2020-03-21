package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.PaperManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */


@Service("Paper")
@Transactional(rollbackFor = Exception.class)
public class PaperManageServiceImpl implements PaperManageService {
    @Resource
    private PaperMapper paperMapper;

    @Override
    public void deletePaperById(int id) {
        paperMapper.deleteById(id);
    }

}
