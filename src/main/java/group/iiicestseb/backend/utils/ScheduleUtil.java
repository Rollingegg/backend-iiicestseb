package group.iiicestseb.backend.utils;


import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.ManageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author jh
 * @date 2019/2/29
 */
@Component
@Slf4j
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
     * 每 1min 检测一次爬虫任务
     */
    @Scheduled(cron = "0 0/1 * * * *")
    public void checkCrawler() throws Exception {
        PyUtil.checkCrawler();
    }


    /**
     * 每天凌晨3点进行 spark 的 page rank 计算
     */
    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "0 40 9 * * *")
    public void pagerank(){
        try{
            String[] cmd = new String[]{
                    "/opt/models/spark/bin/spark-submit",
                    "--master",
                    "spark://hadoop1:7077",
                    "--class",
                    "spark.LoadData",
                    "--deploy-mode",
                    "cluster" +
                    "hdfs://hadoop1:9000/jars/graph-1.0-SNAPSHOT-jar-with-dependencies.jar",
                    "0.001",
                    "0.15"};
            Process ps = Runtime.getRuntime().exec(cmd);
            BufferedReader output = new BufferedReader(new InputStreamReader(ps.getInputStream()));
            BufferedReader error = new BufferedReader(new InputStreamReader(ps.getErrorStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while((line = output.readLine())!=null){
                sb.append(line).append("\n");
            }
            log.info(sb.toString());
            sb = new StringBuilder();
            while((line = error.readLine())!=null){
                sb.append(line).append("\n");
            }
            log.error(sb.toString());

        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
