package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.entity.Author;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@Service
public class AuthorServiceImpl implements AuthorService {

    @Resource
    private AuthorMapper authorMapper;

    @Override
    public AuthorInfoVO getAuthorInfo(String name) {
        AuthorInfoVO authorInfoVO = new AuthorInfoVO();
        //放入作者信息
        Author author = authorMapper.selectByName(name);
        authorInfoVO.setId(author.getId());
        authorInfoVO.setName(author.getName());
        authorInfoVO.setAffiliationId(author.getAffiliationId());
        return authorInfoVO;
    }

}
