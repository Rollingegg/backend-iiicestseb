package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.paper.SearchResultVO;
import group.iiicestseb.backend.vo.user.UserVO;
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
    @Resource(name = "Conference")
    private ConferenceService conferenceService;
    @Lazy
    @Resource(name = "User")
    private UserService userService;
    @Lazy
    @Resource(name = "PaperManage")
    private PaperManageService paperManageService;
    @Lazy
    @Resource(name = "Search")
    private SearchService searchService;
    @Lazy
    @Resource(name = "Statistics")
    private StatisticsService statisticsService;

    //------------------------AffiliationService---------------------------------

    @Override
    public Affiliation findAffiliationByName(String name) {
        return affiliationService.findAffiliationByName(name);
    }

    @Override
    public void saveAffiliation(Affiliation affiliation) {
        affiliationService.saveAffiliation(affiliation);
    }

    //-----------------------------------------AuthorService------------------------------

    @Override
    public Author findAuthorByName(String name) {
        return authorService.findAuthorByName(name);
    }

    @Override
    public void insertAuthorList(List<Author> authorList) {
        authorService.insertAuthorList(authorList);
    }

    //-----------------------------------------ConferenceService------------------------------

    @Override
    public Conference findConferenceByName(String name) {
        return conferenceService.findConferenceByName(name);
    }

    @Override
    public void insertConference(Conference conference) {
        conferenceService.insertConference(conference);
    }

    @Override
    public Conference findConferenceById(Integer conferenceId) {
        return conferenceService.findConferenceById(conferenceId);
    }


    //-----------------------------------------PaperManageService------------------------------

    @Override
    public void deletePaperById(int id) {
        paperManageService.deletePaperById(id);
    }

    @Override
    public void insertPaper(Paper paper) {
        paperManageService.insertPaper(paper);
    }

    @Override
    public void insertPaperTermList(List<PaperTerm> paperTerms) {
        paperManageService.insertPaperTermList(paperTerms);
    }

    @Override
    public void insertReferences(List<Reference> references) {
        paperManageService.insertReferences(references);
    }

    @Override
    public void insertPaperAuthorList(List<PaperAuthors> paperAuthors) {
        paperManageService.insertPaperAuthorList(paperAuthors);
    }

    @Override
    public Paper findPaperByArticleId(Integer articleId) {
        return paperManageService.findPaperByArticleId(articleId);
    }

    @Override
    public Term findTermByName(String name) {
        return paperManageService.findTermByName(name);
    }

    @Override
    public void saveTermList(List<Term> termList) {
        paperManageService.saveTermList(termList);
    }

    //---------------------------SearchService-------------------------------


    @Override
    public List<SearchResultVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
        return searchService.advancedSearchPaper(advancedSearchForm);
    }

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


}
