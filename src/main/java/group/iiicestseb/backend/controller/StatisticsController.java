package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
@RequestMapping("statistics")
public class StatisticsController {
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";

    @Resource(name = "statistics")
    private StatisticsService statisticsService;

    @PostMapping("/analyzeCSV")
    public Response getAffiliationInfo(@RequestParam("filename") String filename) {
        try {
            statisticsService.loadCsv(filename);
            return Response.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.buildFailure(CSV_ANALYZE_ERROR);
        }
    }

}
