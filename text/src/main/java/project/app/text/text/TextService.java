package project.app.text.text;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.text.text_event.TextEventRepository;
import project.app.text.text_tag.TextTag;

@Service
public class TextService {
    private TextRepository textRepository;
    private TextEventRepository eventRepository;
    //TODO: event repository

    @Autowired
    public TextService(TextRepository repository,TextEventRepository event){
        this.textRepository = repository;
        this.eventRepository = event;
    }

    public List<Text> findAll(){
        return textRepository.findAll();    
    }
    
    public List<Text> findByName(String name){
        return textRepository.findByName(name);
    }
    
    public List<Text> findByTag(TextTag tag){
        return textRepository.findByTagsContaining(tag);
    }

    public Optional<Text> find(Long id){
        return textRepository.findById(id);
    }

    @Transactional
    public Text add(Text text){
        eventRepository.save(text);
        return textRepository.save(text);

        //TODO: event repository
    }

    @Transactional
    public void delete(Text text){
        eventRepository.delete(text);
        textRepository.delete(text);
        //TODO: event repository    
    }

    @Transactional
    public void update(Text new_text){
        textRepository.findById(new_text.getId())
            .ifPresent(text -> {
                text.setContent(new_text.getContent());
                text.setName(new_text.getName());
                text.setTextTags(new_text.getTextTags());
                //text.setEntities(new_text.getEntities());
                //text.setTaskSets(new_text.getTasks());
            });
    }        

}
