package project.app.text.text;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.text.task_set.TaskSet;
import project.app.text.text_event.TextEventRepository;
import project.app.text.text_tag.TextTag;

@Service
public class TextService {
    private TextRepository textRepository;
    private TextEventRepository eventRepository;

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
    
    public List<Text> findByTextTag(TextTag tag){
        return textRepository.findByTextTagsContaining(tag);
    }

    public List<Text> findByTaskSet(TaskSet set){
        return textRepository.findByTaskSetsContaining(set);
    }

    public Optional<Text> find(Long id){
        return textRepository.findById(id);
    }

    @Transactional
    public Text add(Text text){
        eventRepository.save(text);
        return textRepository.save(text);
    }

    @Transactional
    public void delete(Text text){
        eventRepository.delete(text);
        textRepository.delete(text);
    }

    @Transactional
    public void update(Text new_text,boolean sendEvent){
        textRepository.findById(new_text.getId())
            .ifPresent(text -> {
                text.setContent(new_text.getContent());
                text.setName(new_text.getName());
                text.setTextTags(new_text.getTextTags());
                text.setTaskSets(new_text.getTaskSets());

                if(sendEvent)
                    eventRepository.update(new_text);
            });
    }        

}
