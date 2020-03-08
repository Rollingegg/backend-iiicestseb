package group.iiicestseb.backend.entity;


import lombok.Data;

/**
 * @author wph
 * @date 2020/2/29
 */
@Data
public class Record {

    /**
     * 历史记录id
     */
    private Integer id;

    /**
     * 搜索记录字段
     */
    private String searchRecord;

    /**
     * 历史浏览记录
     */
    private String browseRecord;

    public Record(String searchRecord, String browseRecord) {
        this.searchRecord = searchRecord;
        this.browseRecord = browseRecord;
    }

    public Record() {
        super();
    }
}