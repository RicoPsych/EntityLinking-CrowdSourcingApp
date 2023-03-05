package project.app.task_set.task_set;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.task_set_event.TaskSetEventRepository;
import project.app.task_set.text.Text;


@Service
public class TaskSetService {
    TaskSetRepository repository;
    TaskSetEventRepository eventRepository; //event for tasks

    /**
     * Service for TaskSet microservice
     * @param repo TaskSet repository
     * @param eventRepository TaskSetEvent repository, for informing other microservices of changes in TaskSet microservice
     */
    @Autowired
    TaskSetService(TaskSetRepository repo,TaskSetEventRepository eventRepository){
        this.repository = repo;
        this.eventRepository = eventRepository;
    }

    public List<TaskSet> findAll(){
        return repository.findAll();    
    }

    public Optional<TaskSet> find(Long id){
        return repository.findById(id);
    }

    public List<TaskSet> findByNamedEntityType(NamedEntityType type){
        return repository.findByNamedEntityTypesContaining(type);    
    }


    public List<TaskSet> findByText(Text text){
        return repository.findByTextsContaining(text);    
    }


    @Transactional
    public TaskSet add(TaskSet set){
        //TODO: Ochrone przed duplikatami w Controlerze trzeba dodac
        eventRepository.save(set);
        return repository.save(set);
    }

    @Transactional
    public void delete(TaskSet set){
        eventRepository.delete(set);
        repository.delete(set);    
    }

    @Transactional
    public void update(TaskSet newSet, boolean sendEvent){
        repository.findById(newSet.getId())
        .ifPresent(set -> {

            set.setTexts(newSet.getTexts());
            set.setNamedEntityTypes(newSet.getNamedEntityTypes());
            if(sendEvent)
                eventRepository.update(newSet);
   
        });
    }        
}
