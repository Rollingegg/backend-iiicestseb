package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.PaperManageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@Service
public class PaperManageServiceImpl implements PaperManageService {
    @Resource
    PaperMapper paperMapper;

    @Override
    public void deleteById(int id) {
        paperMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByName(String name) {
        

    }
}
