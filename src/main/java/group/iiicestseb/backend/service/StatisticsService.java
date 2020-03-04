package group.iiicestseb.backend.service;


import group.iiicestseb.backend.entity.Record;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface StatisticsService {

    /**
     * 加载已存在的csv文件，解析数据并存入数据库，
     *
     * @param filename 文件名
     */
    public void loadExistedCSV(String filename);

    /**
     * 创建用户记录
     * @return 插入记录主键
     */
    public int createUserRecord(Record record);

    /**
     * 解析上传的csv文件
     *
     * @param file 文件
     */
    public void analyzeUploadedCSV(MultipartFile file);
}
