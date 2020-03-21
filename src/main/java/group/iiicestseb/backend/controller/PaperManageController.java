package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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


}
