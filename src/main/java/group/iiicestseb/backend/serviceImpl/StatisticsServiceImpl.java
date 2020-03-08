package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Record;
import group.iiicestseb.backend.mapper.AffiliationMapper;
import group.iiicestseb.backend.mapper.AuthorMapper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.mapper.StatisticsMapper;
import group.iiicestseb.backend.service.StatisticsService;
import group.iiicestseb.backend.utils.CSVUtil;
import group.iiicestseb.backend.vo.AuthorInfoVO;
import group.iiicestseb.backend.vo.AuthorWithPublish;
import group.iiicestseb.backend.vo.TermWithHotVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @author jh
 * @date 2020/2/22
 */
@Transactional(rollbackFor = Exception.class)
@Service("Statistics")
public class StatisticsServiceImpl implements StatisticsService {

    @Resource
    private AffiliationMapper affiliationMapper;

    @Resource
    private AuthorMapper authorMapper;

    @Resource
    private PaperMapper paperMapper;

    @Resource
    StatisticsMapper statisticsMapper;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void loadExistedCSV(String filename) {
        CSVUtil.analyzeExistedCSV(filename);
    }

    @Override
    public int createUserRecord(Record record) {
        return statisticsMapper.insertUserRecord(record);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Map<String, Object> analyzeUploadedCSV(MultipartFile file) {
        return CSVUtil.analyzeUploadedCSV(file);
    }

    @Override
    public List<TermWithHotVO> calculateHotTerms(Integer num) {
        return statisticsMapper.selectTermsWithHotLimit(num);
    }

    @Override
    public List<AuthorWithPublish> calculateMaxPublishAuthor(Integer num) {
        return statisticsMapper.selectMaxPublishAuthorLimit(num);
    }


}