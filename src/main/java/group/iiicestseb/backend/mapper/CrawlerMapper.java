package group.iiicestseb.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import group.iiicestseb.backend.entity.Crawler;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @author jh
 * @date 2020/7/14
 */
@Mapper
public interface CrawlerMapper extends BaseMapper<Crawler> {

    /**
     * 查询所有爬虫任务
     *
     * @return 爬虫任务列表
     */
    @Select("select * from crawler")
    @ResultType(Crawler.class)
    ArrayList<Crawler> selectAll();

    /**
     * 获取最早添加的等待中的任务
     *
     * @return 爬虫任务对象
     */
    @Select("select * from crawler where state='等待中'" +
            "   order by add_time limit 1")
    @ResultType(Crawler.class)
    Crawler selectNextTask();

    /**
     * 插入日志
     *
     * @param crawlerId 爬虫任务id
     * @param log 爬虫日志
     */
    @Insert("insert into crawler_log set crawler_id=#{crawlerId}, log=#{log}")
    void insertLog(Integer crawlerId, String log);

    /**
     * 获取爬虫日志
     *
     * @param crawlerId 爬虫任务id
     * @return 日志
     */
    @Select("select log from crawler_log where crawler_id=#{crawlerId}")
    String selectLogById(Integer crawlerId);

//
//    /**
//     * 更新爬虫任务的状态
//     *
//     * @param crawlerId 爬虫id
//     * @param state 状态
//     * @return 成功 1， 失败 0
//     */
//    @Update("update crawler set state=#{state} where crawler_id=#{crawlerId}")
//    Integer updateById(Integer crawlerId, String state);
}
