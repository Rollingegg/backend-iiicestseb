package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperAuthorMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service("Author")
@Transactional(rollbackFor = Exception.class)
public class AuthorServiceImpl extends ServiceImpl<AuthorMapper, Author> implements AuthorService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private AuthorMapper authorMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;

    @Override
    public Author findAuthorByName(String name) {
        return authorMapper.selectByName(name);
    }

    @Override
    public void insertAuthorList(List<Author> authorList) {
        if (authorList.isEmpty()) {
            return;
        }
        saveBatch(authorList);
    }

    @Override
    public Collection<Author> findAuthorByIdBatch(Collection<Integer> ids) {
        return this.listByIds(ids);
    }

    @Override
    public Collection<AuthorInfoVO> findAuthorInfoByIdBatch(Collection<Integer> ids){
        return authorMapper.selectAuthorInfoByIdBatch(ids);
    }

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
