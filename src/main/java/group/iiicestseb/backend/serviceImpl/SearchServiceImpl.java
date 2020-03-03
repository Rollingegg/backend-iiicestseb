package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.SearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Search")
public class SearchServiceImpl implements SearchService {
    @Resource
    private PaperMapper paperMapper;
    @Override
    public ArrayList<Paper> simpleSearchPaper(String  type, String keyword){
        if (type.equals("all")){

        }
        else{

        }
        return null;
    }
}
