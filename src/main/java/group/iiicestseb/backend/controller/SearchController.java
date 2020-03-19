package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.PaperInfoVO;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.util.Assert;
/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    public static String PAPERFORM_EMPTY = "输入关键字段不能全为空";
    public static String SERRCH_ERROW =  "搜索出现故障，请稍后重试";
    @Resource(name = "Regedit")
    SearchService searchService;

    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名 "all" "paper_tile" "author_name" "paper_abstract" "DOI" "affiliation_name" ,"term"
     * @param keyword 搜索关键字
     * @return 文献搜索结果列表
     */
    @GetMapping("/simple")
    public Response simpleSearchPaper(@RequestParam(name = "type") String  type,
                                      @RequestParam(name = "keyword") String keyword,
                                      @RequestParam(name = "limit",defaultValue = "50")Integer limit){
        try{
            List<PaperInfoVO> searchResult= searchService.simpleSearchPaper(type,keyword,limit);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            return Response.buildFailure("搜索出现故障，请稍后重试");
        }



    }

    /**
     * 论文高即检索
     * @param
     * @return 论文列表
     */
    @GetMapping("/advanced")
    public Response advancedSearchPaper(@RequestParam(name = "paper_title") String papar_title,
                                        @RequestParam(name = "paper_abstract")String paper_abstract,
                                        @RequestParam(name = "doi")String doi,
                                        @RequestParam(name = "author_name")String author_name,
                                        @RequestParam(name = "affiliation_name")String affiliation_name,
                                        @RequestParam(name = "term")String term,
                                        @RequestParam(name = "limit",defaultValue = "50")Integer limit){
        try{
            Assert.isTrue(!(papar_title == null &&
                    paper_abstract == null &&
                    doi == null &&
                    author_name == null &&
                    affiliation_name == null &&
                    term == null  ),PAPERFORM_EMPTY);

            AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
            advancedSearchForm.setDoiKeyword(doi);
            advancedSearchForm.setAuthorKeyword(author_name);
            advancedSearchForm.setAffiliationKeyword(affiliation_name);
            advancedSearchForm.setPaperTitleKeyword(papar_title);
            advancedSearchForm.setPaperAbstractKeyword(paper_abstract);
            advancedSearchForm.setTermKeyword(term);
            List<PaperInfoVO> searchResult= searchService.advancedSearchPaper(advancedSearchForm,limit);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            return Response.buildFailure(SERRCH_ERROW);
        }
    }
}
