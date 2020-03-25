package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.exception.paper.PaperFormException;
import group.iiicestseb.backend.exception.paper.PaperTypeException;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.PaperInfoVO;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    //搜索类型
    private static final String ALL = "all";
    private static final String AFFILIATION ="affiliation_name";
    private static final String TITLE ="title";
    private static final String ABSTRACT ="paper_abstract";
    private static final String DOI ="doi";
    private static final String AUTHOR ="author_name";
    private static final String TERM ="term";

    public static final String TYPE_ERROR ="搜索类型错误";
    public static final String PAPER_FORM_EMPTY = "搜索内容不能为空";
    public static final String SEARCH_ERROR=  "搜索出现未知错误";
    public static final String LIMIT_ERROR= "limit必须大于0，不超过200";
    @Resource(name = "Regedit")
    SearchService searchService;

    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名 "all" "tile" "author_name" "paper_abstract" "DOI" "affiliation_name" ,"term"
     * @param keyword 搜索关键字
     * @return 文献搜索结果列表
     */
    @GetMapping("/simple")
    public Response simpleSearchPaper(@RequestParam(name = "type") String  type,
                                      @RequestParam(name = "keyword")
                                              @NotNull(message = PAPER_FORM_EMPTY)
                                              String keyword,
                                      @RequestParam(name = "limit",defaultValue = "50")
                                                  @Size(min = 1,max = 200,message = LIMIT_ERROR)
                                                  Integer limit){


        if( !(ALL.equals(type) ||
                AFFILIATION.equals(type) ||
                AUTHOR.equals(type) ||
                ABSTRACT.equals(type) ||
                TITLE.equals(type) ||
                TERM.equals(type) ||
                DOI.equals(type))){
            throw new PaperTypeException();
        }

        try{
            List<PaperInfoVO> searchResult= searchService.simpleSearchPaper(type,keyword,limit);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            return Response.buildFailure(SEARCH_ERROR);
        }
    }

    /**
     * 论文高级检索
     * @param advancedSearchForm 高级搜索表单
     * @return 论文列表
     */
    @PostMapping("/advanced")
    public Response advancedSearchPaper(@RequestBody AdvancedSearchForm advancedSearchForm){
        if(advancedSearchForm.getAffiliationKeyword() == null &&
                advancedSearchForm.getAuthorKeyword() == null &&
                advancedSearchForm.getDoiKeyword() == null &&
                advancedSearchForm.getAffiliationKeyword() == null &&
                advancedSearchForm.getPaperAbstractKeyword() == null &&
                advancedSearchForm.getTermKeyword() == null  ){
            throw new PaperFormException();
        }
        try{
            List<PaperInfoVO> searchResult= searchService.advancedSearchPaper(advancedSearchForm);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            return Response.buildFailure(SEARCH_ERROR);
        }
    }
}
