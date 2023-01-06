package project.app.text_tag.text;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TextService {
    private TextRepository textRepository;


    @Autowired
    public TextService(TextRepository repository){
        this.textRepository = repository;
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

}
