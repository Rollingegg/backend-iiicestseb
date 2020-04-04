package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.form.AdvancedSearchForm;
import group.iiicestseb.backend.utils.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/3/28
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
public class PaperMapperTest {
    @Resource
    private PaperMapper paperMapper;


    @Before
    public void setUp() throws Exception {
        JSONUtil.loadTestData();
    }

    @Test
    public void selectByArticleId() {
    }

    @Test
    public void advancedSearch() {
        AdvancedSearchForm advancedSearchForm = new AdvancedSearchForm();
        advancedSearchForm.setAllKeyword("network");
        advancedSearchForm.setType("all");
        advancedSearchForm.setPage(0);
        advancedSearchForm.setAffiliationKeyword(null);
        advancedSearchForm.setDoiKeyword(null);
        advancedSearchForm.setTitleKeyword(null);
        advancedSearchForm.setTermKeyword(null);
        advancedSearchForm.setPaperAbstractKeyword(null);
        advancedSearchForm.setAuthorKeyword(null);
        advancedSearchForm.setLimit(10);
//        SearchVO re = paperMapper.advancedSearch(advancedSearchForm);
//        System.out.println("result_num"+re.size());
//        System.out.println(re);
    }

}
