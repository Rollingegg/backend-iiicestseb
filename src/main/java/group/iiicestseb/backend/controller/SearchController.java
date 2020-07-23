package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.paper.SearchVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("/search")
@CrossOrigin
@Validated
public class SearchController {



    @Resource(name = "Regedit")
    SearchService searchService;


    /**
     * 论文高级检索
     *
     * @param advancedSearchForm 高级搜索表单
     * @return 论文列表
     */
    @PostMapping("/advanced")
    public Response advancedSearchPaper(@RequestBody @Valid AdvancedSearchForm advancedSearchForm) {
        advancedSearchForm.isValid();

        SearchVO searchResult = searchService.advancedSearchPaper(advancedSearchForm);
        return Response.buildSuccess(searchResult);


    }
}
