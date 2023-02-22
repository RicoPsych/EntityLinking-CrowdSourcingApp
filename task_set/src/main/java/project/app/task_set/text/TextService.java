package project.app.task_set.text;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TextService {
    private TextRepository textRepository;

    /**
     * Autowired Constructor for Text Representation Service
     * @param repository Text representation repository
     */
    @Autowired
    public TextService(TextRepository repository){
        this.textRepository = repository;

    }

    
    //TODO: Tests + Debugging
    //redundant?
    public List<Text> findAll(){
        return textRepository.findAll();    
    }

    public Optional<Text> find(Long id){
        return textRepository.findById(id);
    }

    @Transactional
    public Text add(Text text){
        return textRepository.save(text);
    }

    @Transactional
    public void delete(Text text){
        textRepository.delete(text);
    }
  
    @Transactional
    public void update(Text newText){
        textRepository.findById(newText.getId())
        .ifPresent(text -> {
            //text.setTaskSets(newText.getTaskSets()); wtf XD?
            if(!newText.getTaskSets().equals(newText.getTaskSets())){
                text.setTaskSets(newText.getTaskSets());
            }
        });
    }

}
