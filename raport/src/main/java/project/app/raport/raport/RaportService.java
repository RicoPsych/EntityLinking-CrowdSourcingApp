package project.app.raport.raport;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.raport.response.Response;

@Service
public class RaportService {

    private RaportRepository raport_repository;

    @Autowired
    RaportService(RaportRepository repository){
        this.raport_repository = repository;
    }
    
    /**
     * Find a user response by id
     * @param id id of the response
     * @return Response if exists
     */
    public Optional<Raport> find(Long id){
        return raport_repository.findById(id);
    }

    /**
     * Finds all user responses
     * @return list of responses
     */
    public List<Raport> findAll(){
        return raport_repository.findAll();
    }

    public List<Raport> findByResponse(Response response){
        return raport_repository.findByResponse(response);
    }

    @Transactional
    public Raport add(Raport raport){
        return raport_repository.save(raport);
    }

    @Transactional
    public void delete(Raport raport){
        raport_repository.delete(raport);
    }

    @Transactional
    public void update(Raport new_raport,boolean sendEvent){
        raport_repository.findById(new_raport.getId())
            .ifPresent(raport -> {
                raport.setContent(new_raport.getContent());

                // if(sendEvent)
                //     eventRepository.update(new_response);
            });
    } 





}


