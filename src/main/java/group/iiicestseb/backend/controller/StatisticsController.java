package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("statistics")
@CrossOrigin
@Validated
public class StatisticsController {
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";
    public static final String SHOULD_BE_POSITIVE = "参数应该大于0";
    public static final String PARAM_TOO_LARGE = "参数太大";
    public static final String PARAMETER_ERROR = "参数无效或不合法";
    public static final String GET_HOT_TERMS_ERROR = "获取热门术语发生严重的未知错误";
    public static final String GET_MAX_PUBLISH_AUTHOR_ERROR = "获取热门作者发生严重的未知错误";

    @Resource(name = "Statistics")
    private StatisticsService statisticsService;


    /**
     * 计算并返回最热门的num个术语
     * 热度按为所有文章中出现的总次数
     *
     * @param num num个术语
     * @return 最热门的num个术语和其出现次数
     */
    @GetMapping("hotTerms")
    public Response getHotTerms(@RequestParam("num")
                                @Max(value = 500, message = PARAM_TOO_LARGE)
                                @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                        Integer num) {
        List<TermWithHotVO> termsHot = statisticsService.findHotTerms(num);
        if (termsHot == null) {
            return Response.buildSuccess();
        }
        return Response.buildSuccess(termsHot);
    }

    /**
     *
     *
     * @param termId 术语id
     * @param max    数量上限
     * @return 相关术语集合
     */
    @GetMapping("relativeTermsOfTerm")
    public Response getRelativeTermsOfTerm(@RequestParam("termId")
                                               @NotNull(message = PARAMETER_ERROR)
                                                       Integer termId,
                                           @RequestParam(value = "max", defaultValue = "10")
                                               @Max(value = 100, message = PARAM_TOO_LARGE)
                                               @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                                   Integer max) {
        Collection<Term> terms = statisticsService.findRelativeTermsOfTerm(termId, max);
        return Response.buildSuccess(terms);
    }

    /**
     * 获得指定术语的相关的论文
     *
     * @param termId 术语id
     * @param max    数量上限
     * @return 相关论文集合
     */
    @GetMapping("relativePapersOfTerm")
    public Response getPapersByTermIdInScoreOrder(@RequestParam("termId")
                                           @NotNull(message = PARAMETER_ERROR)
                                                   Integer termId,
                                           @RequestParam(value = "max", defaultValue = "10")
                                           @Max(value = 100, message = PARAM_TOO_LARGE)
                                           @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                                   Integer max) {
        return Response.buildSuccess(statisticsService.findPapersByTermIdInScoreOrder(termId, max));
    }

    /**
     * 获得指定术语的相关作者
     *
     * @param termId 术语id
     * @param max    数量上限
     * @return 相关作者集合
     */
    @GetMapping("activeAuthorsOfTerm")
    public Response getActiveAuthorsOfTerm(@RequestParam("termId")
                                    @NotNull(message = PARAMETER_ERROR)
                                            Integer termId,
                                    @RequestParam(value = "max", defaultValue = "10")
                                    @Max(value = 100, message = PARAM_TOO_LARGE)
                                    @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                            Integer max) {
        return Response.buildSuccess(statisticsService.findActiveAuthorsOfTerm(termId, max));
    }

    /**
     * 获得指定术语的相关的机构
     *
     * @param termId 术语id
     * @param max    数量上限
     * @return 相关机构集合
     */
    @GetMapping("activeAffiliationOfTerm")
    public Response getActiveAffiliationOfTerm(@RequestParam("termId")
                                        @NotNull(message = PARAMETER_ERROR)
                                                Integer termId,
                                        @RequestParam(value = "max", defaultValue = "10")
                                        @Max(value = 100, message = PARAM_TOO_LARGE)
                                        @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                                Integer max) {
        return Response.buildSuccess(statisticsService.findActiveAffiliationOfTerm(termId, max));
    }

    /**
     * 计算并返回发表论文最多的的num个学者和其发表的论文
     *
     * @param num num个学者
     * @return 最热门的num个学者和其发表的论文
     */
    @GetMapping("/maxPublishAuthor")
    public Response getMaxPublishAuthor(@RequestParam("num")
                                        @Max(value = 500, message = PARAM_TOO_LARGE)
                                        @Min(value = 1, message = SHOULD_BE_POSITIVE)
                                                int num) {
        List<AuthorHotVO> authorHotVOList = statisticsService.calculateMaxPublishAuthor(num);
        if (authorHotVOList == null) {
            return Response.buildSuccess();
        }
        return Response.buildSuccess(authorHotVOList);
    }

    /**
     * 获取作者的热度研究方向、词云
     * @param id 作者id
     * @param limit 搜索数
     * @return 研究方向热度列表
     */
    @GetMapping("/author/hot/term")
    public Response getAuthorHotTerm(@RequestParam("id") int id,@RequestParam("limit") int limit){
        return Response.buildSuccess(statisticsService.getAuthorHotTerm(id,limit));
    }

    /**
     * 获取机构的热度研究方向、词云
     * @param id 机构id
     * @param limit 搜索数
     * @return 研究方向热度列表
     */
    @GetMapping("/affiliation/hot/term")
    public Response getAffiliationHotTerm(@RequestParam("id") int id,@RequestParam("limit") int limit){
        return Response.buildSuccess(statisticsService.getAffiliationHotTerm(id,limit));
    }

    /**
     * 获取作者每年发表数
     * @param id 作者id
     * @return 作者每年发表数列表
     */
    @GetMapping("/author/publish/count/per/year")
    public Response getAuthorPublishCountPerYear(@RequestParam("id") int id){
        return Response.buildSuccess(statisticsService.getAuthorPublishCountPerYear(id));
    }



    /**
     * 获取机构每年发表数
     * @param id 机构id
     * @return 机构每年发表数列表
     */
    @GetMapping("/affiliation/publish/count/per/year")
    public Response getAffiliationPublishCountPerYear(@RequestParam("id") int id){
        return Response.buildSuccess(statisticsService.getAffiliationPublishCountPerYear(id));
    }
}
