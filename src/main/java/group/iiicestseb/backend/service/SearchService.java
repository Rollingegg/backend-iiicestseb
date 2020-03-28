package group.iiicestseb.backend.service;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.vo.SearchResultVO;

import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface SearchService {

    /**
     * 论文高级检索
     *
     * @param advancedSearchForm 所有搜索关键词表单
     * @return 论文列表
     */
    List<SearchResultVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm);
//

}
