package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.vo.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

/**
 * @author jh
 * @date 2020/3/25
 */
@RestController
@RequestMapping("/db")
public class DBController {
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";
    public static final String SHOULD_BE_POSITIVE = "参数应该大于0";
    public static final String PARAM_TOO_LARGE = "参数太大";
    public static final String PARAMETER_ERROR = "参数无效或不合法";


    @PostMapping("/analyzeCSV")
    public Response analyzeCSV(@NonNull(message="") @RequestParam("filename") String filename) {
        Assert.notNull(filename, PARAMETER_ERROR);
        statisticsService.loadExistedCSV(filename);
        return Response.buildSuccess();
    }

    @PostMapping("/uploadCSV")
    public Response uploadCSV(@RequestParam("file") MultipartFile file) {
        Assert.notNull(file, PARAMETER_ERROR);
        return Response.buildSuccess(statisticsService.analyzeUploadedCSV(file));
    }


    @GetMapping("/StandardCSV")
    public Response getStandardCSV(HttpServletResponse response) {
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
