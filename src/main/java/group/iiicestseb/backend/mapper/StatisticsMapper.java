package group.iiicestseb.backend.mapper;


import group.iiicestseb.backend.entity.Record;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

/**
 * The interface Statistics mapper.
 *
 * @author jh
 * @date 2020 /2/22
 */
public interface StatisticsMapper {

    /**
     * Insert user record int.
     *
     * @return the int
     */
    @Insert("insert into record(search_record, browse_record) " +
            "value (#{searchRecord,jdbcType=VARCHAR},#{browseRecord,jdbcType=VARCHAR})")
    @Options(useGeneratedKeys = true)
    public int insertUserRecord(Record record);
}
