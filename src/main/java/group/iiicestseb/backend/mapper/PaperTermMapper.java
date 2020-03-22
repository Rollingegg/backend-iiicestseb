package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.PaperTerm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/21
 */
@Repository("PaperTermMapper")
public interface PaperTermMapper extends JpaRepository<PaperTerm, Integer> {

}
