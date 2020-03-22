package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.service.PaperManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author jh
 * @date 2020/2/22
 */


@Service("Paper")
@Transactional(rollbackFor = Exception.class)
public class PaperManageServiceImpl implements PaperManageService {
//    @Lazy
//    @Resource(name = "Regedit")
//    private Regedit regedit;
//    @Resource
//    private PaperMapper paperMapper;
//
//    @Override
//    public void deletePaperById(int id) {
//        //paperMapper.deleteById(id);
//    }

}
