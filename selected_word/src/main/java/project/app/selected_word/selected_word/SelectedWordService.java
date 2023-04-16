package project.app.selected_word.selected_word;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.selected_word.response.Response;

@Service
public class SelectedWordService {

    private SelectedWordRepository selected_word_repository;

    @Autowired
    SelectedWordService(SelectedWordRepository repository){
        this.selected_word_repository = repository;
    }

    /**
     * Find a user response by id
     * @param id id of the response
     * @return Response if exists
     */
    public Optional<SelectedWord> find(Long id){
        return selected_word_repository.findById(id);
    }

    /**
     * Finds all user responses
     * @return list of responses
     */
    public List<SelectedWord> findAll(){
        return selected_word_repository.findAll();
    }

    public List<SelectedWord> findByResponse(Response response){
        return selected_word_repository.findByResponse(response);
    }

    @Transactional
    public SelectedWord add(SelectedWord selected_word){
        return selected_word_repository.save(selected_word);
    }

    @Transactional
    public void delete(SelectedWord selected_word){
        selected_word_repository.delete(selected_word);
    }

    @Transactional
    public void update(SelectedWord new_selected_word,boolean sendEvent){
        selected_word_repository.findById(new_selected_word.getId())
            .ifPresent(selected_word -> {
                selected_word.setIndexStart(new_selected_word.getIndexStart());
                selected_word.setIndexEnd(new_selected_word.getIndexEnd());
                selected_word.setResponse(new_selected_word.getResponse());

                // if(sendEvent)
                //     eventRepository.update(new_response);
            });
    } 

    
}
