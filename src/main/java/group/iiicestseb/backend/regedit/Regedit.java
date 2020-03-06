package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.entity.Record;
import group.iiicestseb.backend.exception.paper.NoPaperFoundException;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.entity.Affiliation;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author wph
 * @date 2020/3/2
 */
@Service("Regedit")
public class Regedit implements AffiliationService,AuthorService,UserService,PaperManageService,SearchService{
    @Resource(name = "Affiliation")
    private AffiliationService affiliationService;
    @Resource(name = "Author")
    private AuthorService authorService;
    @Resource(name = "User")
    private UserService userService;
    @Resource(name = "Paper")
    private PaperManageService paperManageService;
    @Resource(name = "Search")
    private SearchService searchService;
    @Resource(name = "Statistics")
    private StatisticsService statisticsService;



    //-----------------------为消除循环依赖产生的类


    @Override
    public boolean judgeUsername(String username) {
        return userService.judgeUsername(username);
    }

    @Override
    public CopyOnWriteArrayList<String> getAuthorByPaperId(int id) {
        return authorService.getAuthorByPaperId(id);
    }

    @Override
    public CopyOnWriteArrayList<PaperInfoVO> simpleSearchPaper(String type, String keyword) {
        CopyOnWriteArrayList<PaperInfoVO> searchResultVOS= searchService.simpleSearchPaper(type,keyword);
        if (searchResultVOS.size()==0){
            throw new NoPaperFoundException();
        }

        return addAuthorInfoInfoPaper(searchResultVOS);

    }

    @Override
    public CopyOnWriteArrayList<PaperInfoVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
        CopyOnWriteArrayList<PaperInfoVO> searchResultVOS= searchService.advancedSearchPaper(advancedSearchForm);
        if (searchResultVOS.size()==0){
            throw new NoPaperFoundException();
        }
        return addAuthorInfoInfoPaper(searchResultVOS);
    }

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
        Record record = new Record("","");
        statisticsService.createUserRecord(record);
        userForm.setRecordId(record.getId());
        userService.register(userForm);
    }

    //————————————————————————————公共方法

    /**
     * 给文献加上所有作者新信息
     * @param searchResultVOS 搜索结果列表（目前内缺作者信息）
     * @return 搜索结果列表（信息完整）
     */
    private CopyOnWriteArrayList<PaperInfoVO> addAuthorInfoInfoPaper(CopyOnWriteArrayList<PaperInfoVO> searchResultVOS){
        for (PaperInfoVO x:searchResultVOS) {
            x = paperManageService.getPaperInfoVO(x.getPaperTitle());
            CopyOnWriteArrayList<String> authorList = authorService.getAuthorByPaperId(x.getId());
            CopyOnWriteArrayList<AuthorInfoVO> temp = new CopyOnWriteArrayList<AuthorInfoVO>();
            for (String y:authorList){
                AuthorInfoVO authorInfoVO = authorService.getAuthorInfo(y);
                String affiliationName = affiliationService.selectAffiliationById(authorInfoVO.getAffiliationId()).getName();
                authorInfoVO.setAffiliationName(affiliationName);
                temp.add(authorInfoVO);
            }
            x.setAuthorInfoList(temp);
        }
        return searchResultVOS;
    }


}
