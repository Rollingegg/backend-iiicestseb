package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.form.PaperForm;
import group.iiicestseb.backend.entity.Paper;
import group.iiicestseb.backend.mapper.PaperMapper;
import group.iiicestseb.backend.service.PaperManageService;
import group.iiicestseb.backend.vo.PaperInfoVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author jh
 * @date 2020/2/22
 */


@Service("Paper")
@Transactional(rollbackFor = Exception.class)
public class PaperManageServiceImpl implements PaperManageService {
    @Resource
    private PaperMapper paperMapper;


    @Override
    public void deletePaperById(int id) {
        paperMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deletePaperByName(String name) {
        paperMapper.deleteByName(name);
    }

    @Override
    public void updatePaperByName(PaperForm paperForm) {
        Paper paper = new Paper(paperForm);
        paperMapper.updateByName(paper);
    }

    @Override
    public void updatePaperById(PaperForm paperForm) {
        Paper paper = new Paper(paperForm);
        paperMapper.updateByPrimaryKey(paper);
    }

    @Override
    public PaperInfoVO getPaperInfoVO(String name) {
        Paper paper = paperMapper.selectByName(name);
        PaperInfoVO paperInfoVO = new PaperInfoVO(paper);
        paperInfoVO.setPublisherName(paperMapper.selectPublisherById(paperInfoVO.getPublisherId()).getName());
        paperInfoVO.setConferenceName(paperMapper.selectConferenceById(paperInfoVO.getConferenceId()).getName());
        return paperInfoVO;
    }
}
