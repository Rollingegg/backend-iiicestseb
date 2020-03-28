package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.SearchResultVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;
/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
@Validated
public class SearchController {

    //搜索类型
    private static final String ALL = "all";
    private static final String AFFILIATION ="affiliation_name";
    private static final String TITLE ="title";
    private static final String ABSTRACT ="paper_abstract";
    private static final String DOI ="doi";
    private static final String AUTHOR ="author_name";
    private static final String TERM ="term";


    public static final String PAPER_FORM_EMPTY = "搜索内容不能为空";
    public static final String SEARCH_ERROR=  "搜索出现未知错误";

    @Resource(name = "Regedit")
    SearchService searchService;


    /**
     * 论文高级检索
     * @param advancedSearchForm 高级搜索表单
     * @return 论文列表
     */
    @PostMapping("/advanced")
    public Response advancedSearchPaper(@RequestBody @Valid AdvancedSearchForm advancedSearchForm){
        advancedSearchForm.isValid();
        try{
            List<SearchResultVO> searchResult= searchService.advancedSearchPaper(advancedSearchForm);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            throw e;
        }
    }
}
