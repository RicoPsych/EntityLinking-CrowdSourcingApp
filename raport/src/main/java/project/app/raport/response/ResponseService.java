package project.app.raport.response;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ResponseService {
    private ResponseRepository responseRepository;

    @Autowired
    public ResponseService(ResponseRepository repository){
        this.responseRepository = repository;

    }

    public List<Response> findAll(){
        return responseRepository.findAll();    
    }

    public Optional<Response> find(Long id){
        return responseRepository.findById(id);
    }

    @Transactional
    public Response add(Response response){
        return responseRepository.save(response);
    }

    @Transactional
    public void delete(Response response){
        responseRepository.delete(response);
    }
}

