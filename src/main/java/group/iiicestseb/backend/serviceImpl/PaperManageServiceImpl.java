package group.iiicestseb.backend.serviceImpl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import group.iiicestseb.backend.entity.*;
import group.iiicestseb.backend.mapper.*;
import group.iiicestseb.backend.regedit.Regedit;
import group.iiicestseb.backend.service.PaperManageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author jh
 * @date 2020/2/22
 */


@Service("Paper")
@Transactional(rollbackFor = Exception.class)
public class PaperManageServiceImpl extends ServiceImpl<PaperMapper, Paper> implements PaperManageService {

    @Resource(name = "Regedit")
    private Regedit regedit;
    @Resource
    private PaperMapper paperMapper;
    @Resource
    private ReferenceMapper referenceMapper;
    @Resource
    private PaperTermMapper paperTermMapper;
    @Resource
    private PaperAuthorMapper paperAuthorMapper;
    @Resource
    private TermMapper termMapper;

    @Override
    public void deletePaperById(int id) {
        paperMapper.deleteById(id);
    }

    @Override
    public void insertPaper(Paper paper) {
        paperMapper.insert(paper);
    }

    @Override
    public void insertPaperTermList(List<PaperTerm> paperTerms) {
        if (paperTerms.isEmpty()) {
            return;
        }
        paperTermMapper.insertList(paperTerms);
    }

    @Override
    public void insertReferences(List<Reference> references) {
        if (references.isEmpty()) {
            return;
        }
        referenceMapper.insertList(references);
    }

    @Override
    public void insertPaperAuthorList(List<PaperAuthors> paperAuthors) {
        if (paperAuthors.isEmpty()) {
            return;
        }
        paperAuthorMapper.insertList(paperAuthors);
    }

    @Override
    public Paper findPaperByArticleId(Integer articleId) {
        return paperMapper.selectByArticleId(articleId);
    }

    @Override
    public Term findTermByName(String name) {
        return termMapper.selectByName(name);
    }

    @Override
    public void saveTermList(List<Term> termList) {
        if (termList.isEmpty()) {
            return;
        }
        termMapper.insertTermList(termList);
    }


}
