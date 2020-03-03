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
@RequestMapping("/admin/paper")
public class PaperManageController {
    @Resource(name = "Paper")
    private PaperManageService paperManageService;

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
            return Response.buildFailure("删除文献失败，请重试");
        }
    }

    /**
     * 管理员更新论文内容
     * @param paperForm 论文表达
     * @return 无
     */
    @PutMapping("/update")
    public Response updatePaper(@RequestBody PaperForm paperForm){
        try {
            paperManageService.updatePaperById(paperForm);
            return Response.buildSuccess();
        }catch(Exception e){
            return Response.buildFailure("修改论文数据失败");
        }
    }
}
