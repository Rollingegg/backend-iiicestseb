package group.iiicestseb.backend.vo.term;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author wph
 * 机构页面，学者页面，发表的所有论文的所有相关术语个数统计
 * 作为机构/作者的研究方向热度
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TermWithCountVO {

    /**
     * 受控标引的id
     */
    Integer id;


    /**
     * 受控标引名称
     */
    String name;

    /**
     * 受控标引出现次数
     */
    Integer count;
}
