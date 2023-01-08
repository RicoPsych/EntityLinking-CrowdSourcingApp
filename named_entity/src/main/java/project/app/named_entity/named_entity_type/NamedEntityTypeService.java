package project.app.named_entity.named_entity_type;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;


@Service
public class NamedEntityTypeService {
    private NamedEntityTypeRepository repository;

    @Autowired
    public NamedEntityTypeService(NamedEntityTypeRepository repository){
        this.repository = repository;
    }

    public List<NamedEntityType> findAll(){
        return repository.findAll();    
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
}
