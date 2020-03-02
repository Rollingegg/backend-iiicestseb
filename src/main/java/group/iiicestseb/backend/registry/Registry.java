package group.iiicestseb.backend.registry;

import group.iiicestseb.backend.Form.UserForm;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.service.UserService;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wph
 * @data 2020/3/2
 */
@Service
public class Registry implements AffiliationService,AuthorService,UserService{
    @Resource
    private AffiliationService affiliationService;
    @Resource
    private AuthorService authorService;
    @Resource
    private UserService userService;

    @Override
    public Affiliation selectById(int id) {
        return null;
    }

    @Override
    public AffiliationInfoVO getAffiliationInfo(String name) {
        AffiliationInfoVO affiliationInfoVO = affiliationService.getAffiliationInfo(name);
        //todo 之后迭代需要获取更多作者信息
        return affiliationService.getAffiliationInfo(name);
    }

    @Override
    public AuthorInfoVO getAuthorInfo(String name) {
        AuthorInfoVO authorInfoVO = authorService.getAuthorInfo(name);
        Affiliation affiliation = affiliationService.selectById(authorInfoVO.getAffiliationId());
        authorInfoVO.setAffiliationName(affiliation.getName());
        //todo 之后迭代需要获取更多作者信息
        return authorInfoVO;
    }

    @Override
    public UserVO signIn(UserForm userForm) {
        return userService.signIn(userForm);
    }

    @Override
    public void register(UserForm userForm) {
        userService.register(userForm);
        return ;
    }
}
