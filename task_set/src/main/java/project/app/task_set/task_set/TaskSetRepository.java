package project.app.task_set.task_set;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.text.Text;

public interface TaskSetRepository extends JpaRepository<TaskSet,Long>{
    public List<TaskSet> findByNamedEntityTypesContaining(NamedEntityType type);
    
    public List<TaskSet> findByTextsContaining(Text text);
}
