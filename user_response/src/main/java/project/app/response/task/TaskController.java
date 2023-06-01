package project.app.response.task;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import project.app.response.task.dto.*;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService service){
        this.taskService = service;

    }

    @GetMapping("{id}")
    public ResponseEntity<GetTaskResponse> getTask(@PathVariable("id") Long id){
        Optional<Task> opt = taskService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetTaskResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postResponse(@RequestBody PostTaskRequest rq, UriComponentsBuilder builder){
        Task task = PostTaskRequest.dtoToEntityMapper().apply(rq);
        task = taskService.add(task);
        return ResponseEntity
            .created(builder
                .pathSegment("api","tasks","{id}")
                .buildAndExpand(task.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable("id") Long id){
        Optional<Task> opt = taskService.find(id);
        if (opt.isPresent()){
            taskService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
    }
    
}
