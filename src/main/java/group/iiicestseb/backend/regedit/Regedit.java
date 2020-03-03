package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.PaperInfoVO;
import group.iiicestseb.backend.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author wph
 * @date 2020/3/2
 */
@Service("Regedit")
public class Regedit implements AffiliationService,AuthorService,UserService,PaperManageService{
    @Resource(name = "Affiliation")
    private AffiliationService affiliationService;
    @Resource(name = "Author")
    private AuthorService authorService;
    @Resource(name = "User")
    private UserService userService;
    @Resource(name = "Paper")
    private PaperManageService paperManageService;




    @Override
    public void deletePaperById(int id) {
        paperManageService.deletePaperById(id);
    }

    @Override
    public void deletePaperByName(String name) {
        paperManageService.deletePaperByName(name);
    }

    @Override
    public void updatePaperByName(PaperForm paperForm) {
        paperManageService.updatePaperByName(paperForm);
    }

    @Override
    public void updatePaperById(PaperForm paperForm) {
        paperManageService.updatePaperById(paperForm);
    }

    @Override
    public PaperInfoVO getPaperInfoVO(String name) {
        PaperInfoVO paperInfoVO = paperManageService.getPaperInfoVO(name);
        //todo 需要加入更多的资料
        return paperInfoVO;
    }

    @Override
    public Affiliation selectAffiliationById(int id) {
        return affiliationService.selectAffiliationById(id);
    }

    @Override
    public AffiliationInfoVO getAffiliationInfo(String name) {
        AffiliationInfoVO affiliationInfoVO = affiliationService.getAffiliationInfo(name);
        //todo 之后迭代需要获取更多机构信息
        return affiliationInfoVO;
    }

    @Override
    public AuthorInfoVO getAuthorInfo(String name) {
        AuthorInfoVO authorInfoVO = authorService.getAuthorInfo(name);
        Affiliation affiliation = affiliationService.selectAffiliationById(authorInfoVO.getAffiliationId());
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
    }
}
