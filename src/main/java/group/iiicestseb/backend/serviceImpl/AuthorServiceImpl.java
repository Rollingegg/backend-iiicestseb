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
    @Resource
    private AffiliationMapper affiliationMapper;
    /**
     * 作者页面所需要的作者详细信息
     *
     * @return 作者详细信息VO
     * @author wph
     */
    @Override
    public AuthorInfoVO getAuthorInfo(String name) {
        AuthorInfoVO authorInfoVO = new AuthorInfoVO();
        //放入作者信息
        Author author = authorMapper.selectByName(name);
        authorInfoVO.setId(author.getId());
        authorInfoVO.setName(author.getName());
        //放入作者所属机构信息
        Affiliation affiliation = affiliationMapper.selectByPrimaryKey(author.getAffiliationId());
        authorInfoVO.setAffiliationId(affiliation.getId());
        authorInfoVO.setAffiliationName(affiliation.getName());
        authorInfoVO.setSecondary(affiliation.getSecondary());
        //todo
        return authorInfoVO;
    }

}
