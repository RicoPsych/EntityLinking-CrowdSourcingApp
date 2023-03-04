package project.app.ne_type.text_tag;

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
        //TODO: Ochrone przed duplikatami w Controlerze trzeba dodac
        return repository.save(tag);
    }

    @Transactional
    public void delete(TextTag tag){
        repository.delete(tag);    
    }

    @Transactional
    public void update(TextTag new_tag){
        repository.findById(new_tag.getId())
        .ifPresent(tag -> {
            //TODO: if new_tag.getTexts.equals(tag.getTexts()) chyba git?
            //Update wysyłany jeśli zmieniono texty w tagu
            if(!new_tag.getTypes().equals(new_tag.getTypes())){
                tag.setTypes(new_tag.getTypes());
            }
        });
    }        
}
