package group.iiicestseb.backend.mapper;

import group.iiicestseb.backend.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author jh
 * @date 2020/3/21
 */
@Repository("ReferenceMapper")
public interface ReferenceMapper extends JpaRepository<Reference, Integer> {
}
