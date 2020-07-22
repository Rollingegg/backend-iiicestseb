package group.iiicestseb.backend.controller;

import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.service.CrawlerService;
import group.iiicestseb.backend.utils.PyUtil;
import group.iiicestseb.backend.vo.Response;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/7/13
 */
@RestController
@RequestMapping("/crawler")
public class CrawlerController {

    @Resource(name = "Crawler")
    CrawlerService crawlerService;

    /**
     * 添加爬虫任务
     *
     * @param form 爬虫任务表单
     * @return 自增主鍵
     */
    @PostMapping("/add")
    public Response addCrawler(@RequestBody CrawlerForm form) {
        return Response.buildSuccess(crawlerService.addCrawler(form));
    }

    @PostMapping("/start")
    public Response startCrawler() {
        PyUtil.checkCrawler();
        return Response.buildSuccess();
    }

    @GetMapping("/current")
    public Response getCurrentTask() {
        return Response.buildSuccess(crawlerService.getCurrentTask());
    }

    /**
     * 获取所有爬虫任务信息
     *
     * @return 所有爬虫任务
     */
    @GetMapping("/all")
    public Response getAllCrawler() {
        return Response.buildSuccess(crawlerService.getAllCrawler());
    }

    /**
     * 取消爬虫任务，如果在运行中则杀死
     *
     * @param crawlerId 爬虫任务id
     * @return 取消任务成功或失败
     */
    @PostMapping("/cancel")
    public Response cancelCrawler(@RequestParam("crawlerId") Integer crawlerId) {
        return Response.buildSuccess(crawlerService.cancelCrawler(crawlerId));
    }

    @GetMapping("test")
    public Response test() {
//        PyUtil.test();
        return Response.buildSuccess();
    }

}
