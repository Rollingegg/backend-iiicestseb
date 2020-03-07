package group.iiicestseb.backend.vo;

import lombok.Data;

/**
 * @author jh
 * @date 2020/3/7
 */
@Data
public class DBBasicInfo {

    private Integer paperCount;
    
    private Integer termCount;

    private Integer publisherCount;

    private Integer userCount;

    private Integer managerCount;

    private Integer authorCount;

    private Integer conferenceCount;

    private Integer affiliationCount;

}
