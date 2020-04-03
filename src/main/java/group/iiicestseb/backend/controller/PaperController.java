package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.constraints.NotNull;
import javax.annotation.Resource;
import javax.validation.constraints.Min;

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
    public Response getPaperDetail(@NotNull(message = PARAMETER_ERROR) @RequestParam("paperId") Integer paperId){
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

}
