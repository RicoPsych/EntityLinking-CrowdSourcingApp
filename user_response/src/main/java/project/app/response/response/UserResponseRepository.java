package project.app.response.response;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import project.app.response.task.Task;

@Repository
public interface UserResponseRepository extends JpaRepository<Response,Long>{

    public List<Response> findByTask(Task task);

}
