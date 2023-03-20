package project.app.task.task_set;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class TaskSetService {
    TaskSetRepository repository;//event for tasks

    /**
     * Service for TaskSet microservice
     * @param repo TaskSet repositor
     */
    @Autowired
    TaskSetService(TaskSetRepository repo){
        this.repository = repo;
    }

    public List<TaskSet> findAll(){
        return repository.findAll();    
    }

    public Optional<TaskSet> find(Long id){
        return repository.findById(id);
    }

    @Transactional
    public TaskSet add(TaskSet set){
        return repository.save(set);
    }

    @Transactional
    public void delete(TaskSet set){
        repository.delete(set);    
    }

    @Transactional
    public void update(TaskSet newSet){
        repository.findById(newSet.getId())
        .ifPresent(set -> {
            //TODO: if?
            set.setTasks(newSet.getTasks());
        });
    }        
}
