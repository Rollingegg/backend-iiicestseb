package group.iiicestseb.backend.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/7/13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CrawlerForm {

    private String conferenceName;

    private Integer startYear;

    private Integer endYear;

}
