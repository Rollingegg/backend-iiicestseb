package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.vo.SearchResultVO;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface SearchService {

    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名
     * @param keyword 搜索关键字
     * @return 论文列表
     */
    public CopyOnWriteArrayList<SearchResultVO> simpleSearchPaper(String  type, String keyword);

    /**
     * 论文高级检索
     * @param advancedSearchForm 所有搜索关键词表单
     * @return 论文列表
     */
    public CopyOnWriteArrayList<SearchResultVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm);
}
