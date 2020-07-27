package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Crawler;
import group.iiicestseb.backend.form.CrawlerForm;
import group.iiicestseb.backend.vo.crawler.CrawlerTaskVO;

import java.util.ArrayList;

/**
 * @author jh
 * @date 2020/7/13
 */
public interface CrawlerService {

    /**
     * 添加爬虫任务
     *
     * @param form 爬虫任务表单
     * @return 自增主鍵
     */
    Integer addCrawler(CrawlerForm form);

    /**
     * 获取所有爬虫任务信息
     *
     * @return 所有爬虫任务
     */
    ArrayList<Crawler> getAllCrawler();

    /**
     * 取消爬虫任务，如果在运行中则杀死
     *
     * @param crawlerId 爬虫任务id
     * @return 取消任务成功或失败
     */
    Boolean cancelCrawler(Integer crawlerId);

    /**
     * 获取最早添加的等待中的任务
     *
     * @return 爬虫任务对象
     */
    Crawler getNextTask();

    /**
     * 更新爬虫任务数据
     *
     * @param crawler 爬虫任务对象
     * @return 成功或失败
     */
    Boolean updateCrawler(Crawler crawler);

    /**
     * 保存爬虫日志
     *
     * @param crawlerId 爬虫任务id
     * @param log 日志
     */
    void saveLog(Integer crawlerId, String log);

    /**
     * 获取当前运行中的爬虫任务，如果没有运行中的任务，返回null
     *
     * @return 当前运行中的爬虫任务，如果没有运行中的任务，返回null
     */
    CrawlerTaskVO getCurrentTask();

    /**
     * 获取爬虫日志
     *
     * @param crawlerId 爬虫id
     * @return 爬虫日志
     */
    String getLog(Integer crawlerId);
}
