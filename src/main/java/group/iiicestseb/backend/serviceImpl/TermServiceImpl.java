package group.iiicestseb.backend.serviceImpl;

import group.iiicestseb.backend.entity.Term;
import group.iiicestseb.backend.mapper.TermMapper;
import group.iiicestseb.backend.service.TermService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("Term")
public class TermServiceImpl implements TermService {

    @Resource
    TermMapper termMapper;

    @Override
    public List<Term> getTermByPaperId(Integer id) {

        return termMapper.selectByPaperId(id);
    }
}
