package group.iiicestseb.backend.regedit;

import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.form.UserForm;
import group.iiicestseb.backend.service.*;
import group.iiicestseb.backend.vo.affiliation.AffiliationInfoVO;
import group.iiicestseb.backend.vo.author.AuthorBasicInfoVO;
import group.iiicestseb.backend.vo.author.AuthorHotInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInAffiliationVO;
import group.iiicestseb.backend.vo.author.AuthorInfoVO;
import group.iiicestseb.backend.vo.graph.Graph;
import group.iiicestseb.backend.vo.paper.*;
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
    @Resource(name = "Term")
    private TermService termService;

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
    @Resource(name = "Manage")
    private ManageService manageService;
    @Lazy
    @Resource(name = "Paper")
    private PaperService paperService;
    @Lazy
    @Resource(name = "Search")
    private SearchService searchService;
    @Lazy
    @Resource(name = "Statistics")
    private StatisticsService statisticsService;

    @Lazy


    //------------------------AffiliationService---------------------------------

    @Override
    public Graph getAffiliationGraphPaperWithTerm(Integer id) {
        return affiliationService.getAffiliationGraphPaperWithTerm(id);
    }

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
    public Collection<AffiliationInfoVO> findAffiliationInfoByIdBatch(Collection<Integer> ids) {
        return affiliationService.findAffiliationInfoByIdBatch(ids);
    }

    @Override
    public AffiliationInfoVO selectAffiliationBasicInfoByAffiliationId(Integer id) {
        return affiliationService.selectAffiliationBasicInfoByAffiliationId(id);
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
    public Collection<AuthorHotInAffiliationVO> selectHotAuthorByAffiliationId(Integer id, Integer limit) {
        return authorService.selectHotAuthorByAffiliationId(id, limit);
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

    @Override
    public Collection<AuthorInfoVO> getAuthorPartner(Integer id, Integer limit) {
        return authorService.getAuthorPartner(id, limit);
    }

    @Override
    public AuthorStatistics getAuthorStatisticsByAuthorId(Integer authorId) {
        return authorService.getAuthorStatisticsByAuthorId(authorId);
    }

    @Override
    public Collection<AuthorStatistics> getAuthorStatisticsByAuthorIdBatch(Collection<Integer> authorIds) {
        return authorService.getAuthorStatisticsByAuthorIdBatch(authorIds);
    }

    @Override
    public Graph getAuthorGraphPartner(Integer id, Integer limit) {
        return authorService.getAuthorGraphPartner(id, limit);
    }

    @Override
    public Graph getAuthorGraphAffiliation(Integer id, Integer limit) {
        return authorService.getAuthorGraphAffiliation(id, limit);
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
        manageService.deletePaperById(id);
    }

    @Override
    public void insertPaper(Paper paper) {
        manageService.insertPaper(paper);
    }

    @Override
    public void insertPaperTermList(List<PaperTerm> paperTerms) {
        manageService.insertPaperTermList(paperTerms);
    }

    @Override
    public void insertReferences(List<Reference> references) {
        manageService.insertReferences(references);
    }

    @Override
    public void insertPaperAuthorList(List<PaperAuthors> paperAuthors) {
        manageService.insertPaperAuthorList(paperAuthors);
    }

    @Override
    public Paper findPaperByArticleId(Integer articleId) {
        return manageService.findPaperByArticleId(articleId);
    }

    @Override
    public Term findTermByName(String name) {
        return manageService.findTermByName(name);
    }

    @Override
    public void saveTermList(List<Term> termList) {
        manageService.saveTermList(termList);
    }

    @Override
    public Collection<PaperBasicVO> getAuthorRecentPaper(Integer id, int limit) {
        return paperService.getAuthorRecentPaper(id, limit);
    }

    @Override
    public Collection<SearchResultVO> getAuthorAllPaper(Integer id) {
        return paperService.getAuthorAllPaper(id);
    }

    @Override
    public Integer reComputePapersScore() {
        return manageService.reComputePapersScore();
    }

    @Override
    public PaperStatistics findPaperStatistics(Integer paperId) {
        return manageService.findPaperStatistics(paperId);
    }

    @Override
    public PaperStatistics updatePaperScore(Integer id) {
        return manageService.updatePaperScore(id);
    }

    @Override
    public Collection<PaperStatistics> updatePaperScoreBatch(Collection<Integer> ids) {
        return manageService.updatePaperScoreBatch(ids);
    }

    @Override
    public Integer reComputeAuthorStatistics() {
        return manageService.reComputeAuthorStatistics();
    }

    //---------------------------SearchService-------------------------------


    @Override
    public SearchVO advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
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


    //-----------------------------------------PaperService--------------------------------------


    @Override
    public PaperDetail findPaperDetail(Integer paperId) {
        return paperService.findPaperDetail(paperId);
    }

    @Override
    public Collection<PaperBasicVO> getAffiliationRecentlyPublish(Integer id, Integer limit) {
        return paperService.getAffiliationRecentlyPublish(id, limit);
    }

    @Override
    public Collection<SearchResultVO> getAffiliationAllPublish(Integer id) {
        return paperService.getAffiliationAllPublish(id);
    }

    @Override
    public Collection<Paper> findPapersByIdBatch(Collection<Integer> paperIds) {
        return paperService.findPapersByIdBatch(paperIds);
    }

    @Override
    public Collection<PaperOverview> getRecommendPapers(Integer paperId, Integer num) {
        return paperService.getRecommendPapers(paperId, num);
    }

    @Override
    public Collection<AuthorInfoVO> getRecommendAuthors(Integer paperId, Integer num) {
        return paperService.getRecommendAuthors(paperId, num);
    }

    @Override
    public Collection<Affiliation> getRecommendAffiliations(Integer paperId, Integer num) {
        return paperService.getRecommendAffiliations(paperId, num);
    }

    @Override
    public Collection<Term> findTermByIdBatch(Collection<Integer> termIds) {
        return paperService.findTermByIdBatch(termIds);
    }

    @Override
    public Graph computeGraphOfPaperTermPaper(Integer id, Integer paperLimit) {
        return paperService.computeGraphOfPaperTermPaper(id, paperLimit);
    }

    //---------------------------TermService-------------------------------

    @Override
    public List<Term> getTermByPaperId(Integer id) {
        return termService.getTermByPaperId(id);
    }

}
