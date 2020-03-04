package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
public class SearchController {

    @Resource(name = "Regedit")
    SearchService searchService;

    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名 "all" "paper_tile" "author_name" "paper_abstract" "DOI" "affiliation_name"
     * @param keyword 搜索关键字
     * @return 文献搜索结果列表
     */
    @GetMapping("/{type}/{keyword}")
    public Response simpleSearchPaper(@PathVariable String  type, @PathVariable String keyword){
        try{
            return Response.buildSuccess(searchService.simpleSearchPaper(type,keyword));
        }catch (Exception e){
            return Response.buildFailure("搜索出现故障，请稍后重试");
        }
    }

    /**
     * 论文高即检索
     * @param advancedSearchForm 高级检索表单
     * @return 论文列表
     */
    @GetMapping("/advanced")
    public Response advancedSearchPaper(@RequestBody AdvancedSearchForm advancedSearchForm){
        try{
            return Response.buildSuccess(searchService.advancedSearchPaper(advancedSearchForm));
        }catch (Exception e){
            return Response.buildFailure("搜索出现故障，请稍后重试");
        }
    }
}
