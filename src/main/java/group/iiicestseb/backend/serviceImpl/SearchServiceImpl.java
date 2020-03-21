package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.SearchService;
import group.iiicestseb.backend.vo.PaperInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Search")
public class SearchServiceImpl implements SearchService {
    public static final String ALL = "all";
    public static final String AFFILIATION ="affiliation_name";
    public static final String TITLE ="paper_title";
    public static final String ABSTRACT ="paper_abstract";
    public static final String DOI ="doi";
    public static final String AUTHOR ="author_name";
    public static final String TERM ="term";

    @Resource
    private PaperMapper paperMapper;
    @Override
    public List<PaperInfoVO> simpleSearchPaper(String  type, String keyword, Integer limit){
        //todo 需要等数据库重构完成才能写
        return null;
    }

    @Override
    public List<PaperInfoVO> advancedSearchPaper(AdvancedSearchForm advancedSearchForm) {
        //todo 需要等数据库重构完成才能写
        return null;
    }
}
