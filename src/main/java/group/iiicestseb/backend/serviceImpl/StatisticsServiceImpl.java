package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.vo.AuthorHotVO;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Statistics")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource(name = "Regedit")
    private Regedit regedit;

//    @Resource
//    private AffiliationMapper affiliationMapper;
//
//    @Resource
//    private AuthorMapper authorMapper;
//
//    @Resource
//    private PaperMapper paperMapper;

    @Resource
    StatisticsMapper statisticsMapper;

    @Override
    public List<TermWithHotVO> calculateHotTerms(Integer num) {
        return statisticsMapper.selectTermsWithHotLimit(num);
    }

    @Override
    public List<AuthorHotVO> calculateMaxPublishAuthor(Integer num) {
        return statisticsMapper.selectMaxPublishAuthorLimit(num);
    }


//
//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public void loadExistedCSV(String filename) {
//        CSVUtil.analyzeExistedCSV(filename);
//    }
//
//    @Override
//    public int createUserRecord(Record record) {
//        return statisticsMapper.insertUserRecord(record);
//    }
//
//    @Override
//    @Transactional(rollbackFor = RuntimeException.class)
//    public Map<String, Object> analyzeUploadedCSV(MultipartFile file) {
//        return CSVUtil.analyzeUploadedCSV(file);
//    }
//

//

}