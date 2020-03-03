package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.SearchResultVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Search")
public class SearchServiceImpl implements SearchService {
    static String ALL = "all";
    static String AFFILIATION ="affiliation_name";
    static String AUTHOR ="author_name";

    @Resource
    private PaperMapper paperMapper;
    @Override
    public CopyOnWriteArrayList<SearchResultVO> simpleSearchPaper(String  type, String keyword){
        CopyOnWriteArrayList<SearchResultVO> searchResultVOCopyOnWriteArrayList = new CopyOnWriteArrayList<SearchResultVO>();
        CopyOnWriteArrayList<Paper> paperCopyOnWriteArrayList;
        if (ALL.equals(type)){
            //all类型的全模糊查询
            paperCopyOnWriteArrayList = paperMapper.simpleSearchPaperAll(keyword);
        }
        else {
            //单一类型的模糊查询
            if (AUTHOR.equals(type)){
                type = type.replace('_','.');
            }
            else if(AFFILIATION.equals(type)){
                type = type.replace('_','.');
            }
            paperCopyOnWriteArrayList = paperMapper.simpleSearchPaperByType(type,keyword);
        }
        for (Paper paper:paperCopyOnWriteArrayList) {
            searchResultVOCopyOnWriteArrayList.add(new SearchResultVO(paper));
        }
        return searchResultVOCopyOnWriteArrayList;
    }

    @Override
    public CopyOnWriteArrayList<SearchResultVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
        CopyOnWriteArrayList<SearchResultVO> searchResultVOCopyOnWriteArrayList = new CopyOnWriteArrayList<SearchResultVO>();
        CopyOnWriteArrayList<Paper> paperCopyOnWriteArrayList = paperMapper.advancedSearch(advancedSearchForm);;
        for (Paper paper:paperCopyOnWriteArrayList) {
            searchResultVOCopyOnWriteArrayList.add(new SearchResultVO(paper));
        }
        return searchResultVOCopyOnWriteArrayList;
    }
}
