package project.app.task_set.ne_type;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NamedEntityTypeService {
    private NamedEntityTypeRepository repository;

    /**
     * Autowired Constructor for NamedEntityTypeService representation in TaskSet microservice
     * @param repo NamedEntityType repository
     */
    @Autowired
    public NamedEntityTypeService(NamedEntityTypeRepository repo){
        this.repository = repo;
    }

    public Optional<NamedEntityType> find(Long id){
        return repository.findById(id);
    }

    @Transactional
    public NamedEntityType add(NamedEntityType type){
        return repository.save(type);
    }

    @Transactional
    public void delete(NamedEntityType type){
        repository.delete(type);
    }

    @Transactional
    public void update(NamedEntityType newType){
        repository.findById(newType.getId())
        .ifPresent(type -> {
            type.setTaskSets(newType.getTaskSets());
        });        
    }
}
