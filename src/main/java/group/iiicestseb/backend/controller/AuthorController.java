package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;

/**
 * @author wph
 * @date 2020/2/29
 */
@ResponseBody
@RestController
@CrossOrigin
@RequestMapping("/author")
public class AuthorController {
    @Resource(name = "Regedit")
    private AuthorService authorService;

    public static final String PARAM_ERROR = "参数无效";

    /**
     * 根据机构id搜索该机构的所有作者
     *
     * @param id 机构id
     * @return 作者列表
     */
    @GetMapping("/allin/affiliation")
    public Response getAllAuthorInAffiliation(@RequestParam("id") Integer id) {
        return Response.buildSuccess(authorService.selectAllAuthorByAffiliationId(id));
    }

    /**
     * 根据机构id搜索该机构热门作者
     *
     * @param id 机构id
     * @return 作者列表
     */
    @GetMapping("/hotin/affiliation")
    public Response getHotAuthorInAffiliation(@RequestParam("id") Integer id, @RequestParam(value = "limit", defaultValue = "10") Integer limit) {
        return Response.buildSuccess(authorService.selectHotAuthorByAffiliationId(id, limit));
    }


    /**
     * 获取作者合作伙伴
     *
     * @param id    作者id
     * @param limit 搜索数
     * @return 合作伙伴列表
     */
    @GetMapping("/partner")
    public Response getAuthorPartner(@RequestParam("id") Integer id, @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return Response.buildSuccess(authorService.getAuthorPartner(id, limit));
    }


    /**
     * 作者详情页面获取作者基本信息
     *
     * @param id 作者id
     * @return 作者详情页面基本信息
     */
    @GetMapping("/info")
    public Response getAuthorBasicInfo(@RequestParam("id") Integer id) {
        return Response.buildSuccess(authorService.getAuthorBasicInfoByAuthorId(id));
    }

    /**
     * 获取指定作者的统计信息
     *
     * @param id 作者id
     * @return 作者统计信息
     */
    @GetMapping("/statistics")
    public Response getAuthorStatistics(
            @NotNull(message = PARAM_ERROR)
            @RequestParam("id")
                    Integer id) {
        return Response.buildSuccess(authorService.getAuthorStatisticsByAuthorId(id));
    }



    /**
     * 获取指定作者合作伙伴关系图
     * @param id 作者id
     * @param limit 上限设置
     * @return 作者合作伙伴关系图
     */
    @GetMapping("/graph/partner")
    public Response getAuthorGraphPartner
            (@RequestParam("id") Integer id, @RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        return Response.buildSuccess(authorService.getAuthorGraphPartner(id,limit));
    }

    /**
     * 获取指定作者同机构作者关系图
     * @param id 作者id
     * @param limit 上限设置
     * @return 作者同机构关系图
     */
    @GetMapping("/graph/affiliation")
    public Response getAuthorGraphAffiliation
    (@RequestParam("id") Integer id, @RequestParam(name = "limit", defaultValue = "100") Integer limit) {
        return Response.buildSuccess(authorService.getAuthorGraphAffiliation(id,limit));
    }

}
