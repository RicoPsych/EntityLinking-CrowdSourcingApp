package project.app.ne_type.ne_type;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.ne_type.task_set.TaskSet;
import project.app.ne_type.text_tag.TextTag;

@Repository
public interface NamedEntityTypeRepository extends JpaRepository<NamedEntityType,Long> {

    public List<NamedEntityType> findByTextTagsContaining(TextTag tag);

    public List<NamedEntityType> findByTaskSetsContaining(TaskSet taskSet);
}
