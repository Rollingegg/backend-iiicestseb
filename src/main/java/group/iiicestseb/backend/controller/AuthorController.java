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
        return Response.buildSuccess(authorService.selectHotAuthorByAffiliationId(id,limit));
    }




    @GetMapping("/partner")
    public Response getAuthorPartner(@RequestParam("id") Integer id,@RequestParam("limit") Integer limit){
        return Response.buildSuccess(authorService.getAuthorPartner(id,limit));
    }



}
