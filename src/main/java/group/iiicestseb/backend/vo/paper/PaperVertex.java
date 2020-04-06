package group.iiicestseb.backend.vo.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author jh
 * @date 2020/4/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaperVertex {

    private Integer id;

    private String title;

    private Integer cite;

    private Double score;

}
