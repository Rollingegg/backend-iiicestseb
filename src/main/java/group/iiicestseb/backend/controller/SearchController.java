package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
public class SearchController {



    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名
     * @param keyword 搜索关键字
     * @return 文献搜索结果列表
     */
//    @GetMapping("/search/{type}/keyword")
//    public Response simpleSearchPaper(@PathVariable String  type, @PathVariable String keyword){
//        return null;
//    }
}
