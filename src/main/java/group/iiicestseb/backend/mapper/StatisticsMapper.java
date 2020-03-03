package group.iiicestseb.backend.mapper;


import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface StatisticsMapper {

    @Insert("insert into record(search_record, browse_record) " +
            "value ('','');" +
            "select last_insert_id()")
    @Options(useGeneratedKeys = true)
    public int insertUserRecord();
}
