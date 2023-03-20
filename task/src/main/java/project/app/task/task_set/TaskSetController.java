package project.app.task.task_set;

import java.util.ArrayList;
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

import project.app.task.task.Task;
import project.app.task.task.TaskService;
import project.app.task.task_set.dto.*;


@RestController
@RequestMapping("api/task_sets")
public class TaskSetController {
    private TaskSetService taskSetService;
    private TaskService taskService;

    /**
     * Autowired Constructor for TaskSetController
     * @param taskSetService 
     * @param taskService
     */
    @Autowired
    public TaskSetController(TaskSetService taskSetService, TaskService taskService ){
        this.taskSetService = taskSetService;
        this.taskService = taskService;
    }

    /**
    //  * TODO: names?
    //  * Lists all TaskSets Ids
    //  * @return response with list of TaskSets
    //  */
    // @GetMapping
    // public ResponseEntity<GetTaskSetsResponse> getTaskSets(){
    //     return ResponseEntity.ok(GetTaskSetsResponse.entityToDtoMapper().apply(taskSetService.findAll()));
    // }

    // /**
    //  * Gets Task Set specified with id
    //  * @param id id of Task Set
    //  * @return response with Task Set parameters (Code 200) or Error 404 
    //  */
    // @GetMapping("{id}")
    // public ResponseEntity<GetTaskSetResponse> getTaskSet(@PathVariable("id") Long id){
    //     Optional<TaskSet> opt = taskSetService.find(id);
    //     if (opt.isPresent()){
    //         return ResponseEntity.ok(GetTaskSetResponse.entityToDtoMapper().apply(opt.get()));
    //     }
    //     else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    /**
     * Creates new Task Set with given request
     * @param rq body of the request, includes tasks and Types(Classes)
     * @param builder builder for uri?
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> postTaskSet(@RequestBody PostTaskSetRequest rq, UriComponentsBuilder builder){
        TaskSet taskSet = PostTaskSetRequest.dtoToEntityMapper( 
        //     tasks_ids -> {
        //     List<Task> tasks = new ArrayList<>();
        //     for(Long _id : tasks_ids){
        //             Optional<Task> _opt = taskService.find(_id);
        //             if(_opt.isPresent()){
        //                 tasks.add(_opt.get());
        //             }
        //             /**If it doesnt find the tag just skips it TODO:postTaskSet */
        //     }
        //     return tasks;
        // }
        ).apply(rq);
        taskSet = taskSetService.add(taskSet);

        return ResponseEntity
            .created(builder
                .pathSegment("api","task_sets","{id}")
                .buildAndExpand(taskSet.getId()).toUri())
            .build();  
    }

    /**
     * Deletes Task Set with given id, this also deletes all tasks connected with this Task Set
     * @param id    id of task set to be deleted
     * @return  void
     * 
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskSet(@PathVariable("id") Long id){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }

        taskSetService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    /**
     * Updates Task Set with new parameters from recived request
     * Deletes taskset from all task relationships, then updates the entity and finally 
     * creates new relationships with tasks.
     * @param id    id of task set to be updated
     * @param rq    body of the request, includes arrays of ids of tasks and types(classes) 
     * @return      void
     */

    //TODO: NIEPOTRZEBNE? Bo TaskSet nie ma zapisanych związków z taskami
    @PutMapping("{id}")
    public ResponseEntity<Void> updateTaskSet(@PathVariable("id") Long id,@RequestBody PutTaskSetRequest rq){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }
        return ResponseEntity.accepted().build();
    }
}
