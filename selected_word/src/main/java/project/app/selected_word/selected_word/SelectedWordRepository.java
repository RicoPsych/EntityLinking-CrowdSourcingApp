package project.app.selected_word.selected_word;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.app.selected_word.response.Response;

@Repository
public interface SelectedWordRepository extends JpaRepository<SelectedWord,Long>{
    
    public List<SelectedWord> findByResponse(Response response);

}
