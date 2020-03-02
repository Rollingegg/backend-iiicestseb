package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@RestController
public class AffiliationController {
    @Resource
    private AffiliationService affiliationService;

    @GetMapping("/affiliation")
    public Response getAffiliationInfo(@RequestParam("name")String name){
        try{
            return  Response.buildSuccess(affiliationService.getAffiliationInfo(name));
        }catch (Exception e){
            return Response.buildFailure("机构查询错误，请重试");
        }
    }


}
