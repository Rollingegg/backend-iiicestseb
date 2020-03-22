package group.iiicestseb.backend.service;



import group.iiicestseb.backend.vo.AuthorWithPublish;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface StatisticsService {

//    /**
//     * 加载已存在的csv文件，解析数据并存入数据库，
//     *
//     * @param filename 文件名
//     */
//    void loadExistedCSV(String filename);
//
//    /**
//     * 创建用户记录
//     * @param record 用户记录
//     * @return 插入记录主键
//     */
//    int createUserRecord(Record record);
//
//    /**
//     * 解析上传的csv文件
//     *
//     * @param file 文件
//     * @return 解析后的数据
//     */
//    Map<String, Object> analyzeUploadedCSV(MultipartFile file);
//
//    /**
//     * 计算并返回最热门的num个术语
//     * 热度按为所有文章中出现的总次数
//     *
//     * @param num num个术语
//     * @return 最热门的num个术语和其出现次数
//     */
//    List<TermWithHotVO> calculateHotTerms(Integer num);
//
//    /**
//     * 计算并返回发表论文最多的的num个学者和其发表的论文
//     *
//     * @param num num个学者
//     * @return 最热门的num个学者和其发表的论文
//     */
//    List<AuthorWithPublish> calculateMaxPublishAuthor(Integer num);
}
