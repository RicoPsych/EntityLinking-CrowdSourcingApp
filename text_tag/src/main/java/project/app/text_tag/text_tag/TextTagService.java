package project.app.text_tag.text_tag;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.text.Text;
import project.app.text_tag.text_tag_event.TextTagEventRepository;

@Service
public class TextTagService {
    TextTagRepository repository;
    TextTagEventRepository eventRepository;

    @Autowired
    TextTagService(TextTagRepository repo,TextTagEventRepository eventRepository){
        this.repository = repo;
        this.eventRepository = eventRepository;
    }

    public List<TextTag> findAll(){
        return repository.findAll();    
    }

    /**
     * Find all TextTags that are connected with provided text
     * @param text Text
     * @return List of Tags
     */
    public List<TextTag> findByText(Text text){
        return repository.findByTextsContaining(text);    
    }

    /**
     * Find all TextTags that are connected with provided type
     * @param type NamedEntityType
     * @return List of Tags
     */
    public List<TextTag> findByNamedEntityType(NamedEntityType type){
        return repository.findByNamedEntityTypesContaining(type);    
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
        eventRepository.save(tag);
        return repository.save(tag);
    }

    @Transactional
    public void delete(TextTag tag){
        eventRepository.delete(tag);
        repository.delete(tag);    
    }

    @Transactional
    public void update(TextTag new_tag, boolean sendEvent){
        repository.findById(new_tag.getId())
        .ifPresent(tag -> {
            tag.setName(new_tag.getName());
            tag.setDescription(new_tag.getDescription());
            //TODO: Update wysyłany jeśli zmieniono texty w tagu
            //if(!new_tag.getTexts().equals(new_tag.getTexts())){
            tag.setTexts(new_tag.getTexts());
            tag.setNamedEntityTypes(new_tag.getNamedEntityTypes());
            if(sendEvent)
                eventRepository.update(new_tag);
            //}
        });
    }        
}
