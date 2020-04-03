package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

    /**
     * 根据机构id搜索该机构的所有作者
     * @param id 机构id
     * @return 作者列表
     */
    @GetMapping("/allin/affiliation")
    public Response getAllAuthorInAffiliation(@RequestParam("id") Integer id){
        return Response.buildSuccess(authorService.selectAllAuthorByAffiliationId(id));
    }

    /**
     * 根据机构id搜索该机构热门作者
     * @param id 机构id
     * @return 作者列表
     */
    @GetMapping("/hotin/affilation")
    public Response getHotAuthorInAffiliation(@RequestParam("id") Integer id,@RequestParam("limit") Integer limit){
        //todo 评分
        return Response.buildSuccess(authorService.selectHotAuthorByAffiliationId(id,limit));
    }



    /**
     * 获取作者合作伙伴
     * @param id 作者id
     * @param limit 搜索数
     * @return 合作伙伴列表
     */
    @GetMapping("/partner")
    public Response getAuthorPartner(@RequestParam("id") Integer id,@RequestParam("limit") Integer limit){
        return Response.buildSuccess(authorService.getAuthorPartner(id,limit));
    }


    /**
     * 作者详情页面获取作者基本信息
     * @param id 作者id
     * @return 作者详情页面基本信息
     */
    @GetMapping("/info")
    public Response getAuthorBasicInfo(@RequestParam("id") Integer id){
        return Response.buildSuccess(authorService.getAuthorBasicInfoByAuthorId(id));
    }

}
