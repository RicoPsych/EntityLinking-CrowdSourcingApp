package project.app.text.task_set;

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
     * @param repo TaskSet repository
     * @param eventRepository TaskSetEvent repository, for informing other microservices of changes in TaskSet microservice
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
        //TODO: Ochrone przed duplikatami w Controlerze trzeba dodac
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
            //set.setTexts(newSet.getTexts());
            if(!newSet.getTexts().equals(newSet.getTexts())){
                set.setTexts(newSet.getTexts());
            }
        });
    }        
}
