package project.app.text.task_set;

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

import project.app.text.task_set.dto.*;
import project.app.text.text.Text;
import project.app.text.text.TextService;


@RestController
@RequestMapping("api/task_sets")
public class TaskSetController {
    private TaskSetService taskSetService;
    private TextService textService;

    /**
     * Autowired Constructor for TaskSetController
     * @param taskSetService 
     * @param textService
     */
    @Autowired
    public TaskSetController(TaskSetService taskSetService, TextService textService ){
        this.taskSetService = taskSetService;
        this.textService = textService;
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
     * @param rq body of the request, includes Texts and Types(Classes)
     * @param builder builder for uri?
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> postTaskSet(@RequestBody PostTaskSetRequest rq, UriComponentsBuilder builder){
        TaskSet task = PostTaskSetRequest.dtoToEntityMapper( 
            text_ids -> {
            //if text_ids == null -> texts = null;
            List<Text> texts = new ArrayList<>();
            for(Long _id : text_ids){
                    Optional<Text> _opt = textService.find(_id);
                    if(_opt.isPresent()){
                        texts.add(_opt.get());
                    }
                    /**If it doesnt find the tag just skips it TODO:postTaskSet */
            }
            return texts;
        })
        .apply(rq);
        task = taskSetService.add(task);

        return ResponseEntity
            .created(builder
                .pathSegment("api","task_sets","{id}")
                .buildAndExpand(task.getId()).toUri())
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
        if (opt.isPresent()){
            taskSetService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Updates Task Set with new parameters from recived request
     * @param id    id of task set to be updated
     * @param rq    body of the request, includes arrays of ids of texts and types(classes) 
     * @return      void
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> updateTaskSet(@PathVariable("id") Long id,@RequestBody PutTaskSetRequest rq){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }


        taskSetService.update(PutTaskSetRequest.dtoToEntityUpdater( 
            text_ids -> {
                //if text_ids == null -> texts = null;
                List<Text> texts = new ArrayList<>();
                for(Long _id : text_ids){
                        Optional<Text> _opt = textService.find(_id);
                        if(_opt.isPresent()){
                            texts.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO: updateTaskSet*/
                }
                return texts;
            })
            .apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }
}