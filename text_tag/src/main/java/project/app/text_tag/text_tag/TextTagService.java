package project.app.text_tag.text_tag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TextTagService {
    TextTagRepository repository;

    @Autowired
    TextTagService(TextTagRepository repo){
        this.repository = repo;
    }

    public List<TextTag> findAll(){
        return repository.findAll();    
    }
    
    public Optional<TextTag> findByName(String name){
        return repository.findByName(name);
    }

    public Optional<TextTag> find(Long id){
        return repository.findById(id);
    }

    @Transactional
    public TextTag add(TextTag tag){
        return repository.save(tag);
        //TODO: event repository
    }

    @Transactional
    public void delete(TextTag tag){
        repository.delete(tag);
        //TODO: event repository    
    }

    @Transactional
    public void update(TextTag new_tag){
        repository.findById(new_tag.getId())
            .ifPresent(tag -> {
                tag.setName(new_tag.getName());
                tag.setDescription(new_tag.getDescription());
            });
    }        
}
