package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Author")
@Transactional(rollbackFor = Exception.class)
public class AuthorServiceImpl implements AuthorService {

//    @Resource
//    private AuthorMapper authorMapper;
//
//    @Override
//    public AuthorInfoVO getAuthorInfo(String name) {
//        AuthorInfoVO authorInfoVO = new AuthorInfoVO();
//        //放入作者信息
//        Author author = authorMapper.selectByName(name);
//        authorInfoVO.setId(author.getId());
//        authorInfoVO.setName(author.getName());
//        authorInfoVO.setAffiliationId(author.getAffiliationId());
//        return authorInfoVO;
//    }
//
//
//    @Override
//    public CopyOnWriteArrayList<String> getAuthorByPaperId(int id) {
//
//        return authorMapper.getAuthorByPaperId(id);
//    }
}
