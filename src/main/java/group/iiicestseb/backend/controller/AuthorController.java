package group.iiicestseb.backend.controller;


import group.iiicestseb.backend.service.AuthorService;
import group.iiicestseb.backend.vo.AuthorInfoVO;
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
public class AuthorController {
    @Resource
    private AuthorService authorService;

    /**
     * 作者信息页面 获取作者详细信息
     * @param name
     * @return 作者界面详细信息
     */
    @GetMapping("/author")
    public Response getAuthorInfo(@RequestParam("name") String name){
        try {
            return Response.buildSuccess(authorService.getAuthorInfo(name));
        }catch(Exception e){
            return Response.buildFailure("作者查询出现错误，请重试");
        }
    }


}
