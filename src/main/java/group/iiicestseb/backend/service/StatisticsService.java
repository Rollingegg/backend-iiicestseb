package group.iiicestseb.backend.service;


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
    public void loadCsv(String filename);

    /**
     * 创建用户记录
     * @return 插入记录主键
     */
    public int createUserRecord();

}
