package project.app.response.response;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserResponseRepository extends JpaRepository<Response,Long>{
    //public List<Response> findByTask(long task_id);
    //public List<Response> findByText(long text_id);

}
