package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.AffiliationInfoVO;
import group.iiicestseb.backend.vo.PaperDetail;
import group.iiicestseb.backend.vo.author.AuthorBasicInfoVO;
import group.iiicestseb.backend.vo.author.AuthorHotInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.author.AuthorInAffiliationVO;
import group.iiicestseb.backend.vo.paper.PaperOverview;
import group.iiicestseb.backend.vo.paper.PaperRecentInAffiliationVO;
import group.iiicestseb.backend.vo.paper.SearchResultVO;
import group.iiicestseb.backend.vo.user.UserVO;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
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
    @Lazy
    @Resource(name = "Paper")
    private PaperService paperService;

    //------------------------AffiliationService---------------------------------

    @Override
    public Affiliation findAffiliationByName(String name) {
        return affiliationService.findAffiliationByName(name);
    }

    @Override
    public void saveAffiliation(Affiliation affiliation) {
        affiliationService.saveAffiliation(affiliation);
    }

    @Override
    public Collection<Affiliation> findAffiliationByIdBatch(Collection<Integer> ids) {
        return affiliationService.findAffiliationByIdBatch(ids);
    }

    @Override
    public AffiliationInfoVO selectBasicInfoByAffiliationId(Integer id) {
        return affiliationService.selectBasicInfoByAffiliationId(id);
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

    @Override
    public Collection<Author> findAuthorByIdBatch(Collection<Integer> ids) {
        return authorService.findAuthorByIdBatch(ids);
    }

    @Override
    public List<AuthorHotInAffiliationVO> selectHotAuthorByAffiliationId(Integer id, Integer limit) {
        return authorService.selectHotAuthorByAffiliationId(id,limit);
    }

    @Override
    public List<AuthorInAffiliationVO> selectAllAuthorByAffiliationId(Integer id) {
        return authorService.selectAllAuthorByAffiliationId(id);
    }

    @Override
    public Collection<AuthorInfoVO> findAuthorInfoByIdBatch(Collection<Integer> ids) {
        return authorService.findAuthorInfoByIdBatch(ids);
    }

    @Override
    public AuthorBasicInfoVO getAuthorBasicInfoByAuthorId(Integer id) {
        return authorService.getAuthorBasicInfoByAuthorId(id);
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



    //---------------------------paperService-----------------------


    @Override
    public PaperDetail findPaperDetail(Integer paperId) {
        return paperService.findPaperDetail(paperId);
    }

    @Override
    public Collection<PaperOverview> getRecommendPapers(Integer paperId, Integer num) {
        return paperService.getRecommendPapers(paperId,num);
    }

    @Override
    public Collection<AuthorInfoVO> getRecommendAuthors(Integer paperId, Integer num) {
        return paperService.getRecommendAuthors(paperId,num);
    }

    @Override
    public Collection<Affiliation> getRecommendAffiliations(Integer paperId, Integer num) {
        return paperService.getRecommendAffiliations(paperId,num);
    }

    @Override
    public Collection<PaperRecentInAffiliationVO> getAffiliationRecentlyPublish(Integer id, Integer limit) {
        return paperService.getAffiliationRecentlyPublish(id,limit);
    }

    @Override
    public Collection<SearchResultVO> getAffiliationAllPublish(Integer id) {
        return paperService.getAffiliationAllPublish(id);
    }
}
