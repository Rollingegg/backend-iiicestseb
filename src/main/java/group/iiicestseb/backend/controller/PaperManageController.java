package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author jh
 * @date 2020/2/22
 */
@ResponseBody
@RestController
@CrossOrigin
@RequestMapping("/admin/paper")
public class PaperManageController {
    @Resource(name = "Paper")
    private PaperManageService paperManageService;
    public static  final String DELETE_PAPER_ERROR = "删除文献出现位置错误";

    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";
    public static final String SHOULD_BE_POSITIVE = "参数应该大于0";
    public static final String PARAM_TOO_LARGE = "参数太大";
    public static final String PARAMETER_ERROR = "参数无效或不合法";

    /**
     * 管理员删除论文
     * @param id 论文id
     * @return 无
     */
    @DeleteMapping("/delete")
    public Response deletePaper(@RequestParam("id")int id){
        try {
            paperManageService.deletePaperById(id);
            return Response.buildSuccess();
        }catch (Exception e){
            return Response.buildFailure(DELETE_PAPER_ERROR);
        }
    }



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


//    @GetMapping("/StandardCSV")
//    public Response getStandardCSV(HttpServletResponse response) {
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
