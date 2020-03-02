package group.iiicestseb.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 * @author jh
 * @date 2020/2/22
 */
@RestController
public class SearchController {
    @GetMapping("/search")
    public void searchPaper(@RequestParam(defaultValue = "",required = false)String title){

    }
}
