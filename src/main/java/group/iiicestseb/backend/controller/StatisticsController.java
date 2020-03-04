package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.AuthorWithPublish;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("statistics")
@CrossOrigin
public class StatisticsController {
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";
    public static final String SHOULD_BE_POSITIVE = "参数应该大于0";
    public static final String PARAM_TOO_LARGE = "参数太大";
    public static final String PARAMETER_ERROR = "参数无效或不合法";

    @Resource(name = "Statistics")
    private StatisticsService statisticsService;

    @PostMapping("/analyzeCSV")
    public Response analyzeCSV(@RequestParam("filename") String filename) {
        Assert.notNull(filename, PARAMETER_ERROR);
        statisticsService.loadExistedCSV(filename);
        return Response.buildSuccess();
    }

    @PostMapping("/uploadCSV")
    public Response uploadCSV(@RequestParam("file") MultipartFile file) {
        Assert.notNull(file, PARAMETER_ERROR);
        statisticsService.analyzeUploadedCSV(file);
        return Response.buildSuccess();
    }

    @GetMapping("hotTerms")
    public Response getHotTerms(@RequestParam("num") Integer num) {
        Assert.isTrue(num > 0, SHOULD_BE_POSITIVE);
        Assert.isTrue(num < 500, PARAM_TOO_LARGE);
        try {
            List<TermWithHotVO> termsHot = statisticsService.calculateHotTerms(num);
            return Response.buildSuccess(termsHot);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }

    @GetMapping("/maxPublishAuthor")
    public Response getMaxPublishAuthor(@RequestParam("num") Integer num) {
        Assert.isTrue(num > 0, SHOULD_BE_POSITIVE);
        Assert.isTrue(num < 100, PARAM_TOO_LARGE);
        try {
            List<AuthorWithPublish> authorWithPublishList = statisticsService.calculateMaxPublishAuthor(num);
            return Response.buildSuccess(authorWithPublishList);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }


}
