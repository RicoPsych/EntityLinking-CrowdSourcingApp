package project.app.task_set.text;


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

import project.app.task_set.task_set.TaskSet;
import project.app.task_set.task_set.TaskSetService;
import project.app.task_set.text.dto.*;

@RestController
@RequestMapping("api/texts")
public class TextController {
    private TextService textService;
    private TaskSetService taskSetService;

    /**
     * Autowired Constructor for Text representation Controller for TaskSet microservice
     * @param service text representation service
     * @param taskSetService task set service
     */
    @Autowired
    public TextController(TextService service,TaskSetService taskSetService){
        this.textService = service;
        this.taskSetService = taskSetService;
    }

    //TODO: Tests + Debugging
    @GetMapping("{id}")
    public ResponseEntity<GetTextResponse> getText(@PathVariable("id") Long id){
        Optional<Text> opt = textService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetTextResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postText(@RequestBody PostTextRequest rq, UriComponentsBuilder builder){
        Text text = PostTextRequest.dtoToEntityMapper(
            taskSet_ids -> {
                //if taskSet_ids == null -> taskSets = null;
                List<TaskSet> taskSets = new ArrayList<>();
                for(Long _id : taskSet_ids){
                        Optional<TaskSet> _opt = taskSetService.find(_id);
                        if(_opt.isPresent()){
                            taskSets.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:PostText task_set */
                }
                return taskSets;
            }
        )
        .apply(rq);
        text = textService.add(text);
        return ResponseEntity
            .created(builder
                .pathSegment("api","texts","{id}")
                .buildAndExpand(text.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteText(@PathVariable("id") Long id){
        Optional<Text> opt = textService.find(id);
        if (opt.isPresent()){
            textService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateText(@PathVariable("id") Long id,@RequestBody PutTextRequest rq){
        Optional<Text> opt = textService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        textService.update(PutTextRequest.dtoToEntityMapper(            
            taskSet_ids -> {
                //if taskSet_ids == null -> taskSets = null;
                List<TaskSet> taskSets = new ArrayList<>();
                for(Long _id : taskSet_ids){
                        Optional<TaskSet> _opt = taskSetService.find(_id);
                        if(_opt.isPresent()){
                            taskSets.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:PostText task_set */
                }
            return taskSets;
        }).apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }
}
