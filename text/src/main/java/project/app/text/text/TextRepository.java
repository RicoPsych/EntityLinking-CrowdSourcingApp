package project.app.text.text;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.text.task_set.TaskSet;
import project.app.text.text_tag.TextTag;

@Repository
public interface TextRepository extends JpaRepository<Text,Long>{
    public List<Text> findByName(String name);


    public List<Text> findByTextTagsContaining(TextTag tag);

    public List<Text> findByTaskSetsContaining(TaskSet set);

}
