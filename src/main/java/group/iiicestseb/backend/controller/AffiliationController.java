package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@ResponseBody
@RestController
public class AffiliationController {
    @Resource(name = "Regedit")
    private AffiliationService affiliationService;

    /**
     * 机构详细信息页面 获取机构详细信息
     * @param name 机构名称
     * @return 机构详细信息
     */
    @GetMapping("/affiliation")
    public Response getAffiliationInfo(@RequestParam("name")String name){
        try{
            return  Response.buildSuccess(affiliationService.getAffiliationInfo(name));
        }catch (Exception e){
            return Response.buildFailure("机构查询错误，请重试");
        }
    }


}
