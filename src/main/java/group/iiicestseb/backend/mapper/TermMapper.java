package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Term;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/21
 */
@Repository("TermMapper")
public interface TermMapper extends JpaRepository<Term, Integer> {

    /**
     * 根据术语名查找术语
     *
     * @param name 术语名
     * @return 术语
     */
    Term findByName(String name);
}
