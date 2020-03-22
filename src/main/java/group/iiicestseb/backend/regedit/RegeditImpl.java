package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author wph
 * @date 2020/3/2
 */
@Service("Regedit")
public class RegeditImpl implements Regedit {

    @Lazy
    @Resource(name = "Affiliation")
    private AffiliationService affiliationService;
    @Lazy
    @Resource(name = "Author")
    private AuthorService authorService;
    @Lazy
    @Resource(name = "User")
    private UserService userService;
    @Lazy
    @Resource(name = "Paper")
    private PaperManageService paperManageService;
    @Lazy
    @Resource(name = "Search")
    private SearchService searchService;
    @Lazy
    @Resource(name = "Statistics")
    private StatisticsService statisticsService;



    //-----------------------------------------PaperManageService------------------------------

//    @Override
//    public void deletePaperById(int id) {
//        paperManageService.deletePaperById(id);
//    }

    //-----------------------------------------UserService--------------------------------------

    @Override
    public UserVO signIn(UserForm userForm) {
        return userService.signIn(userForm);
    }

    @Override
    public void register(UserForm userForm) {
        userService.register(userForm);
    }
    @Override
    public void isExist(String username) {
        userService.isExist(username);
    }

    //---------------------------SearchService-------------------------------

    @Override
    public List<PaperInfoVO> simpleSearchPaper(String type, String keyword, Integer limit) {
        return searchService.simpleSearchPaper(type,keyword,limit);
    }

    @Override
    public List<PaperInfoVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
        return searchService.advancedSearchPaper(advancedSearchForm);
    }
}
