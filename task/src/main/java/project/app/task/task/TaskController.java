package project.app.task.task;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import project.app.task.task.dto.GetTaskResponse;
import project.app.task.task.dto.GetTasksResponse;
import project.app.task.task.dto.PostTaskRequest;
import project.app.task.task.dto.PostTasksRequest;
import project.app.task.task.dto.PutTaskRequest;
import project.app.task.task_set.TaskSet;
import project.app.task.task_set.TaskSetService;

@RestController
@RequestMapping("api/task_sets/{task_set_id}/tasks")
public class TaskController {
    TaskService taskService;
    TaskSetService taskSetService;

    @Autowired
    TaskController(TaskService taskService,TaskSetService taskSetService){
        this.taskService = taskService;
        this.taskSetService = taskSetService;        
    }

    @GetMapping
    public ResponseEntity<GetTasksResponse> getTasks(@PathVariable("task_set_id") Long TaskSetId){
        Optional<TaskSet> opt = taskSetService.find(TaskSetId);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }

        List<Task> tasks = taskService.findByTaskSet(opt.get());
        return ResponseEntity.ok(GetTasksResponse.entityToDtoMapper().apply(tasks));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTaskResponse> getNamedEntity(@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){    
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
        return ResponseEntity.ok(GetTaskResponse.entityToDtoMapper().apply(opt.get()));
    }

    @PostMapping
    public ResponseEntity<Void> postTask(@RequestBody PostTaskRequest rq, @PathVariable("task_set_id") Long task_set_id , UriComponentsBuilder builder){
        Optional<TaskSet> opt = taskSetService.find(task_set_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet Not found").build();
        }

        Task task = PostTaskRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        task = taskService.add(task);

        return ResponseEntity
        .created(builder
            .pathSegment("api","task_sets","{task_set_id}","tasks","{id}")
            .buildAndExpand(task.getTaskSet().getId(),task.getId()).toUri())
        .build();  
    }

    @PostMapping("bulk")
    public ResponseEntity<Void> postTasks(@RequestBody PostTasksRequest rq, @PathVariable("task_set_id") Long task_set_id , UriComponentsBuilder builder){
        Optional<TaskSet> opt = taskSetService.find(task_set_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet Not found").build();
        }

        List<Task> tasks = PostTasksRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        for(int i = 0; i< tasks.size() ; i++){
            taskService.add(tasks.get(i));
        }
        return ResponseEntity
        .created(builder
            .pathSegment("api","task_sets","{task_set_id}","tasks","{first_id}-{last_id}")
            .buildAndExpand(task_set_id,tasks.get(0).getId(),tasks.get(tasks.size()-1).getId())
            .toUri())
        .build();  
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        taskService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTask(@RequestBody PutTaskRequest rq,@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        taskService.update(PutTaskRequest.dtoToEntityMapper( _task_set_id -> {
            Optional<TaskSet> _opt = taskSetService.find(_task_set_id);
            if(_opt.isEmpty())
            {
                return null;
            }
            return _opt.get();
        }).apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }
}
