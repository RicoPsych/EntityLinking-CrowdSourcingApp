package project.app.raport.raport;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.raport.response.Response;

@Repository
public interface RaportRepository extends JpaRepository<Raport, Long>{

    public List<Raport> findByResponse(Response response);
    
}