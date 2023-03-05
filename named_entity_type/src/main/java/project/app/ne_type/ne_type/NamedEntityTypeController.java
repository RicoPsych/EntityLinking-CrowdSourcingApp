package project.app.ne_type.ne_type;

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

import project.app.ne_type.ne_type.dto.*;
import project.app.ne_type.task_set.TaskSet;
import project.app.ne_type.task_set.TaskSetService;
import project.app.ne_type.text_tag.TextTag;
import project.app.ne_type.text_tag.TextTagService;

@RestController
@RequestMapping("api/netypes")
public class NamedEntityTypeController {
    private NamedEntityTypeService netService;
    private TaskSetService taskSetService;
    private TextTagService textTagService;

    @Autowired
    NamedEntityTypeController(NamedEntityTypeService service,TaskSetService taskSetService,TextTagService textTagService){
        this.netService = service;
        this.taskSetService = taskSetService;
        this.textTagService = textTagService;
    }

    @GetMapping()
    public ResponseEntity<GetNamedEntityTypesResponse> getTypes(){
            return ResponseEntity.ok(GetNamedEntityTypesResponse.entityToDtoMapper().apply(netService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetNamedEntityTypeResponse> getType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetNamedEntityTypeResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().header("Description", "Type not found").build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postType(@RequestBody PostNamedEntityTypeRequest rq, UriComponentsBuilder builder){
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper(
            tags_ids -> {
                List<TextTag> tags = new ArrayList<>();
                for(Long _id : tags_ids){
                        Optional<TextTag> _opt = textTagService.find(_id);
                        if(_opt.isPresent()){
                            tags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return tags;
            },

            type_id -> {
                    Optional<NamedEntityType> _opt = netService.find(type_id);
                    if(_opt.isEmpty()){
                        return null;
                    }
                    return _opt.get();
                }
            ,
            task_sets_ids -> {
                //if text_ids == null -> texts = null;
                List<TaskSet> sets = new ArrayList<>();
                for(Long _id : task_sets_ids){
                        Optional<TaskSet> _opt = taskSetService.find(_id);
                        if(_opt.isPresent()){
                            sets.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postTaskSet */
                }
                return sets;
            }
        ) //restrictions?
            .apply(rq);
        type = netService.add(type);
        return ResponseEntity
            .created(builder
                .pathSegment("api","netypes","{id}")
                .buildAndExpand(type.getId()).toUri())
            .build();  
    }

    //TODO: usuwanie związków???
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Type not found").build();
        }
        netService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateType(@PathVariable("id") Long id, @RequestBody PutNamedEntityTypeRequest rq){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Type not found").build();
        }
        netService.update(PutNamedEntityTypeRequest.dtoToEntityUpdater( 
            tags_ids -> {
                List<TextTag> tags = new ArrayList<>();
                for(Long _id : tags_ids){
                        Optional<TextTag> _opt = textTagService.find(_id);
                        if(_opt.isPresent()){
                            tags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return tags;
            },

            _type_id -> {

                    Optional<NamedEntityType> _opt = netService.find(_type_id);
                    if(_opt.isPresent()){
                        return _opt.get();
                    }
                    return null;
                }
            ,
            task_sets_ids -> {
                List<TaskSet> sets = new ArrayList<>();
                for(Long _id : task_sets_ids){
                        Optional<TaskSet> _opt = taskSetService.find(_id);
                        if(_opt.isPresent()){
                            sets.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return sets;
            }
        ).apply(opt.get(),rq),true);

        return ResponseEntity.accepted().build();
    }
}
