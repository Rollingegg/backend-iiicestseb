package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.AffiliationService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/2/29
 */
@ResponseBody
@RestController
@CrossOrigin
@RequestMapping("/affiliation")
@Validated
public class AffiliationController {
    public static final String PARAMETER_ERROR = "参数无效或不合法";

    @Resource(name = "Regedit")
    private AffiliationService affiliationService;

    /**
     * 机构详细信息页面 获取机构详基本信息
     * @param id 机构id
     * @return 机构基本信息
     */
    @GetMapping("/info")
    public Response getAffiliationBasicInfo( @RequestParam("id") Integer id){
        return  Response.buildSuccess(affiliationService.selectAffiliationBasicInfoByAffiliationId(id));
    }


    /**
     * 机构图，带有相关的机构和术语信息
     * @param id 机构id
     * @return 机构图
     */
    @GetMapping("/graph/paper/with/term")
    public Response getAffiliationGraphPaperWithTerm( @RequestParam("id") Integer id){
        return  Response.buildSuccess(affiliationService.getAffiliationGraphPaperWithTerm(id));
    }

}
