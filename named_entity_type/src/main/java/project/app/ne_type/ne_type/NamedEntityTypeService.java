package project.app.ne_type.ne_type;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.ne_type.ne_type_event.NamedEntityTypeEventRepository;

@Service
public class NamedEntityTypeService {
    private NamedEntityTypeRepository repository;
    private NamedEntityTypeEventRepository eventRepository;


    @Autowired
    public NamedEntityTypeService(NamedEntityTypeRepository repo){
        this.repository = repo;
    }

    public List<NamedEntityType> findAll(){
        return repository.findAll();
    }


    public Optional<NamedEntityType> find(Long id){
        return repository.findById(id);
    }

    @Transactional
    public NamedEntityType add(NamedEntityType type){
        eventRepository.save(type);
        return repository.save(type);
    }

    @Transactional
    public void delete(NamedEntityType type){
        eventRepository.delete(type);
        repository.delete(type);
    }

    @Transactional
    public void update(NamedEntityType newType){
        repository.findById(newType.getId())
        .ifPresent(type -> {
            type.setDescription(newType.getDescription());
            type.setName(newType.getName());
            type.setNamedEntityTypeChildren(newType.getNamedEntityTypeChildren());
            type.setNamedEntityTypeParent(newType.getNamedEntityTypeParent());
            type.setTaskSets(newType.getTaskSets());
            type.setTextTags(newType.getTextTags());
            
            //TODO: DODAĆ IF zmiany w związkach to wtedy update! 
            eventRepository.update(newType);
        });

        
    }

}
