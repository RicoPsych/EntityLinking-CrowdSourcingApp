package project.app.text_tag.ne_type;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class NamedEntityTypeService {
    private NamedEntityTypeRepository repository;

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

}
