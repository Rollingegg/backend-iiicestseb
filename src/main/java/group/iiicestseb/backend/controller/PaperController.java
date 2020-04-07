package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.graph.Graph;
import group.iiicestseb.backend.vo.graph.Vertex;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;

/**
 * @author jh
 * @date 2020/3/29
 */
@RestController
@RequestMapping("/paper")
public class PaperController {

    public static final String PARAMETER_ERROR = "参数无效或不合法";

    @Resource(name = "Paper")
    private PaperService paperService;

    @GetMapping("/detail")
    public Response getPaperDetail(@NotNull(message = PARAMETER_ERROR) @RequestParam("paperId") Integer paperId) {
        return Response.buildSuccess(paperService.findPaperDetail(paperId));
    }

    @GetMapping("/recommend/papers")
    public Response recommendPapers(@NotNull(message = PARAMETER_ERROR) @RequestParam("paperId") Integer paperId, @Min(1) @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendPapers(paperId, num));
    }

    @GetMapping("/recommend/authors")
    public Response getRecommendAuthors(@NotNull(message = PARAMETER_ERROR) @RequestParam("paperId") Integer paperId, @Min(value = 1, message = PARAMETER_ERROR) @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendAuthors(paperId, num));
    }

    @GetMapping("/recommend/affiliations")
    public Response getAffiliations(@NotNull(message = PARAMETER_ERROR) @RequestParam("paperId") Integer paperId, @Min(value = 1, message = PARAMETER_ERROR) @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendAffiliations(paperId, num));
    }

    /**
     * 搜索机构最近发表的文献
     *
     * @param id    机构id
     * @param limit 搜索个数
     * @return 机构最近发表的文献列表
     */
    @GetMapping("/affiliation/recently/publish")
    public Response getAffiliationsRecentlyPublish(@RequestParam("id") @NotNull(message = PARAMETER_ERROR) Integer id,
                                                   @RequestParam(name = "limit", defaultValue = "10") @Min(value = 1, message = PARAMETER_ERROR) Integer limit) {
        return Response.buildSuccess(paperService.getAffiliationRecentlyPublish(id, limit));
    }


    /**
     * 搜索机构发表的所有文献
     *
     * @param id 机构id
     * @return 机构所有发表的文献列表
     */
    @GetMapping("/affiliation/all/publish")
    public Response getAffiliationsAllPublish(@RequestParam("id") @NotNull(message = PARAMETER_ERROR) Integer id) {
        return Response.buildSuccess(paperService.getAffiliationAllPublish(id));
    }

    /**
     * 作者详情页面 获取作者最近论文
     *
     * @param id    作者id
     * @param limit 搜索数
     * @return 作者最近发表论文列表
     */
    @GetMapping("/author/recently/publish")
    public Response getAuthorRecentlyPublish(@RequestParam("id") @NotNull(message = PARAMETER_ERROR) Integer id,
                                             @RequestParam(name = "limit", defaultValue = "10") @Min(value = 1, message = PARAMETER_ERROR) Integer limit) {
        return Response.buildSuccess(paperService.getAuthorRecentPaper(id, limit));
    }

    /**
     * 作者详情页面 获取作者所有论文
     *
     * @param id 作者id
     * @return 作者所有论文
     */
    @GetMapping("/author/all/publish")
    public Response getAuthorAllPublish(@RequestParam("id") @NotNull(message = PARAMETER_ERROR) Integer id) {
        return Response.buildSuccess(paperService.getAuthorAllPaper(id));
    }

    @GetMapping("graph/paper-term-paper/center")
    public Response getPaperGraphOfPTP(
            @RequestParam("id")
            @NotNull(message = PARAMETER_ERROR)
                    Integer id) {
        return Response.buildSuccess(paperService.computeGraphOfPaperTermPaper(id));
    }
    /*,
            @RequestParam(name = "limit", defaultValue = "100")
            @Min(value = 1, message = PARAMETER_ERROR)
            @Max(value = 1000, message = PARAMETER_ERROR)
                    Integer limit*/


}
