package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.regedit.Regedit;
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
public class PaperManageServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperManageService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private PaperMapper paperMapper;
//
//    @Override
//    public void deletePaperById(int id) {
//        //paperMapper.deleteById(id);
//    }

}
