package project.app.task_set.ne_type;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NamedEntityTypeRepository extends JpaRepository<NamedEntityType,Long> {
    
}
