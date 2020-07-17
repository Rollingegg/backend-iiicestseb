package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.constant.rank.RankType;
import group.iiicestseb.backend.service.RankService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author wph
 * @date 2020/5/13
 */
@ResponseBody
@RestController
@CrossOrigin
@RequestMapping("/rank")
@Validated
public class RankController {


    @Resource(name = "Regedit")
    RankService rankService;

    @PostMapping("/get")
    public Response getRank(@RequestParam("page") Integer page,
                            @RequestParam("size") Integer size,
                            @RequestParam("rankType") RankType rankType){
        return Response.buildSuccess(rankService.getRank(page,size,rankType));
    }

    @PostMapping("/overview")
    public Response getOverview(){
        return Response.buildSuccess(rankService.getOverView());
    }
}
