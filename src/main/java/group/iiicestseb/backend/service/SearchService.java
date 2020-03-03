package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Paper;

import java.util.ArrayList;

/**
 * @author jh
 * @date 2020/2/22
 */
public interface SearchService {

    /**
     * 论文低级检索
     * @param type 类型 有 全部/标题/作者/摘要/DOI/机构名
     * @param keyword 搜索关键字
     */
    public ArrayList<Paper> simpleSearchPaper(String  type, String keyword);
}
