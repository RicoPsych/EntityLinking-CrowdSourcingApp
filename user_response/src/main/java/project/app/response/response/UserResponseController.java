package project.app.response.response;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import project.app.response.response.dto.*;
import project.app.response.task.*;
import project.app.response.task.Task;

@RestController
@RequestMapping("api/tasks/{task_id}/response")
public class UserResponseController {

    private UserResponseService userResponseService;

    private TaskService taskService;

    @Autowired
    public UserResponseController(UserResponseService service, TaskService taskService){

        this.userResponseService = service;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity <GetUserResponsesResponse> getUserResponses(@PathVariable("task_id") Long task_id){

        Optional<Task> opt = taskService.find(task_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        List<Response> responses = userResponseService.findByTask(opt.get());
        
        return ResponseEntity.ok(GetUserResponsesResponse.entityToDtoMapper().apply(responses));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponseResponse> getUserResponse(@PathVariable("id") Long id, @PathVariable("task_id") Long task_id){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }
        return ResponseEntity.ok(GetUserResponseResponse.entityToDtoMapper().apply(opt.get()));
        
    }

    @PostMapping
    public ResponseEntity<Void> postUserResponce(@RequestBody PostUserResponseRequest rq, @PathVariable("task_id") Long task_id, UriComponentsBuilder builder){


        Optional<Task> opt = taskService.find(task_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
            
        Response response = PostUserResponseRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        response = userResponseService.add(response);

        return ResponseEntity
            .created(builder
                .pathSegment("api","task","{task_id}","response", "{id}")
                .buildAndExpand(response.getTask().getId(),response.getId()).toUri())
            .build(); 

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserResponse(@PathVariable("id") Long id){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }
        userResponseService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUserRespose(@PathVariable("id") Long id, @RequestBody PutUserResponseRequest rq){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }

        userResponseService.update(PutUserResponseRequest.dtoToEntityUpdater().apply(opt.get(),rq), true);

        return ResponseEntity.accepted().build();
    }


    
}
