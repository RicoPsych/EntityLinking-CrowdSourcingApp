package project.app.response.response;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserResponseService {
    private UserResponseRepository response_repository;

    @Autowired
    UserResponseService(UserResponseRepository repository){
        this.response_repository = repository;
    }

    /**
     * Find a user response by id
     * @param id id of the response
     * @return Response if exists
     */
    public Optional<Response> find(Long id){
        return response_repository.findById(id);
    }

    /**
     * Finds all user responses
     * @return list of responses
     */
    public List<Response> findAll(){
        return response_repository.findAll();
    }

    /**
     * Finds all responses to a task 
     * @param task_id id of a task
     * @return List of responses
     */
    public List<Response> findByTask(Long task_id){
        return response_repository.findByTask(task_id);
    }

    public List<Response> findByText(Long text_id){
        return response_repository.findByText(text_id);
    }


    @Transactional
    public void add(Response response){
        response_repository.save(response);
    }

    @Transactional
    public void delete(Response response){
        response_repository.delete(response);
    }
}
