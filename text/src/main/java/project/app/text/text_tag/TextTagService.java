package project.app.text.text_tag;
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

    public Optional<TextTag> find(Long id){
        return repository.findById(id);
    }

    @Transactional
    public TextTag add(TextTag tag){
        return repository.save(tag);
    }

    @Transactional
    public void delete(TextTag tag){
        repository.delete(tag);
    }

    @Transactional
    public void update(TextTag newTag){
        repository.findById(newTag.getId())
        .ifPresent(tag -> {
            tag.setTexts(newTag.getTexts());
        });
    }

}