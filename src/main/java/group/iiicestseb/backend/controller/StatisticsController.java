package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.author.AuthorHotVO;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.term.TermWithHotVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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

        List<TermWithHotVO> termsHot = statisticsService.calculateHotTerms(num);
        if (termsHot == null) {
            return Response.buildSuccess();
        }
        return Response.buildSuccess(termsHot);

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


//
//    @PostMapping("/analyzeCSV")
//    public Response analyzeCSV(@RequestParam("filename") String filename) {
//        Assert.notNull(filename, PARAMETER_ERROR);
//        statisticsService.loadExistedCSV(filename);
//        return Response.buildSuccess();
//    }
//
//    @PostMapping("/uploadCSV")
//    public Response uploadCSV(@RequestParam("file") MultipartFile file) {
//        Assert.notNull(file, PARAMETER_ERROR);
//        return Response.buildSuccess(statisticsService.analyzeUploadedCSV(file));
//    }
//

//
//    @GetMapping("/StandardCSV")
//    public Response getStandardCSV(HttpServletResponse response){
//        response.setHeader("content-type", "application/octet-stream; charset=utf-8");
//        String headerKey = "Content-Disposition";
//        String headerValue = "attachment; filename=Standard.csv";
//        response.setHeader(headerKey, headerValue);
//        response.setContentType("application/octet-stream");
//        ClassPathResource file = new ClassPathResource("csv/Standard.csv");
//        try {
//            response.getOutputStream().write(file.getInputStream().readAllBytes());
//            response.setContentLength(Math.toIntExact(file.contentLength()));
//        } catch (IOException e) {
//            e.printStackTrace();
//            Response.buildFailure("未知错误，可能是文件不存在或文件过大");
//        }
//        return Response.buildSuccess();
//    }


}
