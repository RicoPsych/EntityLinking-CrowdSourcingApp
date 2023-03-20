package project.app.task.task;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import project.app.task.task_event.TaskEventRepository;
import project.app.task.task_set.TaskSet;

@Service
public class TaskService {
    TaskRepository repository;
    TaskEventRepository eventRepository;

    @Autowired
    TaskService(TaskRepository repo, TaskEventRepository eventRepository){
        this.repository = repo;
        this.eventRepository = eventRepository;
    }
    
    public List<Task> findAll(){
        return repository.findAll();
    }

    public Optional<Task> find(Long id){
        return repository.findById(id);
    }

    public List<Task> findByTaskSet(TaskSet set){
        return repository.findByTaskSet(set);
    }

    @Transactional
    public Task add(Task task){
        eventRepository.save(task);
        return repository.save(task);
    }

    @Transactional
    public void delete(Task task){
        eventRepository.delete(task);
        repository.delete(task);
    }

    @Transactional
    public void update(Task newTask){
        repository.findById(newTask.getId())
        .ifPresent(task->{

            task.setStartDate(newTask.getStartDate());
            task.setEndDate(newTask.getEndDate());
            task.setIndexStart(newTask.getIndexStart());
            task.setIndexEnd(newTask.getIndexEnd());
            task.setTaskSet(newTask.getTaskSet());
            task.setSubmitionsNum(newTask.getSubmitionsNum());
        });
    }
}
