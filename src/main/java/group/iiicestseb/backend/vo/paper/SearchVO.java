package group.iiicestseb.backend.vo.paper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @author wph
 * 包含搜索结果和搜索结果的统计信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchVO {
    /**
     * 搜索结果总数
     */
    Integer paperCount;

    /**
     * 搜索结果论文列表
     */
    Collection<SearchResultVO> searchResultVOCollection;
}
