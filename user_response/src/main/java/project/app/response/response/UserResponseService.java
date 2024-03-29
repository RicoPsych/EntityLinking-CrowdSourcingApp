package project.app.response.response;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.transaction.Transactional;
import project.app.response.response_event.ResponseEventRepository;
import project.app.response.task.Task;

@Service
public class UserResponseService {

    private UserResponseRepository response_repository;
    private ResponseEventRepository eventRepository;

    @Autowired
    UserResponseService(UserResponseRepository repository,ResponseEventRepository eventRepository){
        this.response_repository = repository;
        this.eventRepository = eventRepository;
    }


    public Optional<Response> find(Long id){
        return response_repository.findById(id);
    }

    public List<Response> findAll(){
        return response_repository.findAll();
    }

    public List<Response> findByTask(Task task){
        return response_repository.findByTask(task);
    }


    @Transactional
    public Response add(Response response){

        boolean set_validity = (response_repository.findAll().size() < 10);
        response.setValidity(set_validity);
        
        eventRepository.save(response);
        return response_repository.save(response);
    }

    @Transactional
    public void delete(Response response){
        eventRepository.delete(response);
        response_repository.delete(response);
    }

    @Transactional
    public void update(Response new_response,boolean sendEvent){



        response_repository.findById(new_response.getId())
            .ifPresent(response -> {

                if(new_response.getDate()!=null){

                    response.setDate(new_response.getDate());
                }
                response.setValidity(new_response.isValidity());

                // if(sendEvent)
                //     eventRepository.update(new_response);
            });
    }



}
