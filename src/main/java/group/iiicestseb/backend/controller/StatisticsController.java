package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.AuthorWithPublish;
import group.iiicestseb.backend.vo.Response;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
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
        return Response.buildSuccess(statisticsService.analyzeUploadedCSV(file));
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

    @GetMapping("/StandardCSV")
    public Response getStandardCSV(HttpServletResponse response){
        response.setHeader("content-type", "application/octet-stream; charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Standard.csv";
        response.setHeader(headerKey, headerValue);
        response.setContentType("application/octet-stream");
        ClassPathResource file = new ClassPathResource("csv/Standard.csv");
        try {
            response.getOutputStream().write(file.getInputStream().readAllBytes());
            response.setContentLength(Math.toIntExact(file.contentLength()));
        } catch (IOException e) {
            e.printStackTrace();
            Response.buildFailure("未知错误，可能是文件不存在或文件过大");
        }
        return Response.buildSuccess();
    }


}
