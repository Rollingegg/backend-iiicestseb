package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.PaperInfoVO;
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
//    public final String ALL = "all";
//    public final String AFFILIATION ="affiliation_name";
//    public final String AUTHOR ="author_name";
//    public final String TERM ="term";
//
//    @Resource
//    private PaperMapper paperMapper;
//    @Override
//    public CopyOnWriteArrayList<PaperInfoVO> simpleSearchPaper(String  type, String keyword,Integer limit){
//
//        if (ALL.equals(type)){
//            //all类型的全模糊查询
//            return paperMapper.simpleSearchPaperAll(keyword,limit);
//        }
//        else {
//            //单一类型的模糊查询
//            if (AUTHOR.equals(type)) {
//                type = "au1.name";
//            } else if (AFFILIATION.equals(type)) {
//                type = "af1.name";
//            } else if (TERM.equals(type)) {
//                type = "t1.word";
//            }
//
//            return paperMapper.simpleSearchPaperByType(type, keyword,limit);
//        }
//    }
//
//    @Override
//    public CopyOnWriteArrayList<PaperInfoVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm, Integer limit) {
//        return paperMapper.advancedSearch(advancedSearchForm,limit);
//    }
}
