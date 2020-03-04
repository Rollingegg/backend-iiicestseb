package group.iiicestseb.backend.controller;



import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("statistics")
@CrossOrigin
public class StatisticsController {
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";

    @Resource(name = "Statistics")
    private StatisticsService statisticsService;

    @PostMapping("/analyzeCSV")
    public Response analyzeCSV(@RequestParam("filename") String filename) {
        try {
            statisticsService.loadExistedCSV(filename);
            return Response.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }

    @PostMapping("/uploadCSV")
    public Response uploadCSV(@RequestParam("file") MultipartFile file) {
        try {
            statisticsService.analyzeUploadedCSV(file);
            return Response.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.buildFailure(e.getMessage());
        }
    }

}
