package project.app.task.task;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import project.app.task.task_set.TaskSet;

public interface TaskRepository extends JpaRepository<Task,Long> {
    
    public List<Task> findByTaskSet(TaskSet set); 
}
