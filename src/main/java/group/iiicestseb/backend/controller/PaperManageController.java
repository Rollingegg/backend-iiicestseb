package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.utils.JSONUtil;
import group.iiicestseb.backend.vo.Response;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
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
    @Resource(name = "Regedit")
    private PaperManageService paperManageService;
    public static final String DELETE_PAPER_ERROR = "删除文献出现位置错误";
    public static final String CSV_ANALYZE_ERROR = "CSV解析错误，请查阅日志";
    public static final String SHOULD_BE_POSITIVE = "参数应该大于0";
    public static final String PARAM_TOO_LARGE = "参数太大";
    public static final String PARAMETER_ERROR = "参数无效或不合法";
    public static final String UNKNOWN_UPLOAD_ERROR = "未知错误，可能是文件不存在或文件过大";
    public static final String UNKNOWN_ERROR = "未知错误";

    /**
     * 管理员删除论文
     *
     * @param id 论文id
     * @return 无
     */
    @DeleteMapping("/delete")
    public Response deletePaper(@RequestParam("id") int id) {

        paperManageService.deletePaperById(id);
        return Response.buildSuccess();
    }

    /**
     * 加载本地数据源
     *
     * @param filename 本地JSON文件名
     * @return 无
     */
    @PostMapping("/loadJSON")
    public Response analyzeJSON(@NotNull(message = PARAMETER_ERROR) @RequestParam("filename") String filename) {
        return Response.buildSuccess(JSONUtil.analyzeExistedJsonFile(filename));
    }

    /**
     * 上传JSON数据文件
     *
     * @param file JSON数据文件
     * @return 无
     */
    @PostMapping("/uploadJSON")
    public Response uploadJSON(@NotNull(message = PARAMETER_ERROR) @RequestParam("file") MultipartFile file) {
        return Response.buildSuccess(JSONUtil.analyzeUploadedJsonFile(file));
    }

    /**
     * 下载标准JSON数据原样例
     *
     * @param response 无
     * @return 无
     */
    @GetMapping("/StandardJSON")
    public Response getStandardJSON(HttpServletResponse response) {
        JSONUtil.getStandardJSON(response);
        return Response.buildSuccess();
    }

    /**
     * 下载标准CSV数据源样例
     *
     * @param response 无
     * @return 无
     */
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
            Response.buildFailure(UNKNOWN_ERROR);
        }
        return Response.buildSuccess();
    }
}
