package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/29
 */
@RestController("/paper")
public class PaperController {

    @Resource
    private PaperService paperService;

    @GetMapping("/detail")
    public Response getPaperDetail(@RequestParam("paperId") Integer paperId){
        return Response.buildSuccess(paperService.findPaperDetail(paperId));
    }

    @GetMapping("/recommend/papers")
    public Response recommendPapers(@RequestParam("paperId") Integer paperId, @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendPapers(paperId, num));
    }

    @GetMapping("/recommend/authors")
    public Response getRecommendAuthors(@RequestParam("paperId") Integer paperId, @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendAuthors(paperId, num));
    }

    @GetMapping("/recommend/affiliations")
    public Response getAffiliations(@RequestParam("paperId") Integer paperId, @RequestParam(value = "num", defaultValue = "10") Integer num) {
        return Response.buildSuccess(paperService.getRecommendAffiliations(paperId, num));
    }

}
