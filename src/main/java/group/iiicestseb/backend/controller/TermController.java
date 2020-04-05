package group.iiicestseb.backend.controller;

import com.alibaba.fastjson.JSONObject;
import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

/**
 * @author jh
 * @date 2020/4/5
 */
@RestController
@RequestMapping("term")
public class TermController {

    @Resource(name = "Regedit")
    private Regedit regedit;

    @GetMapping("info")
    public Response getTermById(@RequestParam("id") Integer id) {
        Term t = regedit.findTermByIdBatch(new ArrayList<Integer>() {{
            add(id);
        }}).iterator().next();
        JSONObject object = new JSONObject();
        object.put("id", t.getId());
        object.put("researchDomain", t.getName());
        return Response.buildSuccess(object);
    }

}
