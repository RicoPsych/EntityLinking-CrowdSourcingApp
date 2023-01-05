package project.app.text.text;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TextService {
    private TextRepository textRepository;
    //TODO: event repository

    @Autowired
    public TextService(TextRepository repository){
        this.textRepository = repository;
    }

    public List<Text> findAll(){
        return textRepository.findAll();    
    }
    
    public List<Text> findByName(String name){
        return textRepository.findByName(name);
    }

    public Optional<Text> find(Long id){
        return textRepository.findById(id);
    }

    @Transactional
    public Text add(Text text){
        return textRepository.save(text);
        //TODO: event repository
    }

    @Transactional
    public void delete(Text text){
        textRepository.delete(text);
        //TODO: event repository    
    }

    @Transactional
    public void update(Text new_text){
        textRepository.findById(new_text.getId())
            .ifPresent(text -> {
                text.setContent(new_text.getContent());
                text.setName(new_text.getName());
            });
    }        

}
