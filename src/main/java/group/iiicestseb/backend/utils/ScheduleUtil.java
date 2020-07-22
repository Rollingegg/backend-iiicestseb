package group.iiicestseb.backend.utils;


import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.ManageService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2019/2/29
 */
@Component
public class ScheduleUtil {

    @Resource(name = "Regedit")
    private Regedit regedit;

    /**
     * 每天凌晨5点更新统计信息
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0 5 * * *")
    public void updateStatistics() {
        ((ManageService)regedit).reComputePapersScore();
        ((ManageService)regedit).reComputeAuthorStatistics();
    }

    /**
     * 每 10s 检测一次爬虫任务
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 0/1 * * * *")
    public void checkCrawler() {
        PyUtil.checkCrawler();
    }


}
