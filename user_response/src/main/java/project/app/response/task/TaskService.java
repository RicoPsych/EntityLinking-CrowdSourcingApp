package project.app.response.task;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class TaskService {
    private TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository repository){
        this.taskRepository = repository;

    }

    public List<Task> findAll(){
        return taskRepository.findAll();    
    }

    public Optional<Task> find(Long id){
        return taskRepository.findById(id);
    }

    @Transactional
    public Task add(Task task){
        return taskRepository.save(task);
    }

    @Transactional
    public void delete(Task task){
        taskRepository.delete(task);
    }
}