package group.iiicestseb.backend.service;

import group.iiicestseb.backend.entity.Term;

import java.util.List;

/**
 * @author wph
 */
public interface TermService {
    /**
     * 通过文献id获取文献所有术语
     * @param id 术语id
     * @return 术语列表
     */
    List<Term> getTermByPaperId(Integer id);


}
