package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.exception.paper.NoPaperFoundException;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.PaperInfoVO;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
    @GetMapping("/simple")
    public Response simpleSearchPaper(@RequestParam(name = "type") String  type, @RequestParam(name = "keyword") String keyword){
        try{
            List<PaperInfoVO> searchResult= searchService.simpleSearchPaper(type,keyword);
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
     * @param advancedSearchForm 高级检索表单
     * @return 论文列表
     */
    @GetMapping("/advanced")
    public Response advancedSearchPaper(@RequestBody AdvancedSearchForm advancedSearchForm){
        try{
            List<PaperInfoVO> searchResult= searchService.advancedSearchPaper(advancedSearchForm);
            if (searchResult.size()==0){
                return Response.buildSuccess();
            }
            return Response.buildSuccess(searchResult);
        }
        catch (Exception e){
            return Response.buildFailure("搜索出现故障，请稍后重试");
        }
    }
}
