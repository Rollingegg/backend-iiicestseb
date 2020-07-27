package group.iiicestseb.backend.vo.crawler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/7/20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CrawlerTaskVO {

    private Integer crawlerId;

    private Integer total;

    private Integer current;

    private String state;

}
