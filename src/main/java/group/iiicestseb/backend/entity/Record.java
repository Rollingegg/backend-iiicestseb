package group.iiicestseb.backend.entity;

public class Record {
    private Integer id;

    private String searchRecord;

    private String browseRecord;

    public Record(Integer id, String searchRecord, String browseRecord) {
        this.id = id;
        this.searchRecord = searchRecord;
        this.browseRecord = browseRecord;
    }

    public Record() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSearchRecord() {
        return searchRecord;
    }

    public void setSearchRecord(String searchRecord) {
        this.searchRecord = searchRecord == null ? null : searchRecord.trim();
    }

    public String getBrowseRecord() {
        return browseRecord;
    }

    public void setBrowseRecord(String browseRecord) {
        this.browseRecord = browseRecord == null ? null : browseRecord.trim();
    }
}