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
    NamedEntityTypeService netService;
    TaskSetService taskSetService;
    TextTagService textTagService;

    @Autowired
    NamedEntityTypeController(NamedEntityTypeService service){
        this.netService = service;
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
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postType(@RequestBody PostNamedEntityTypeRequest rq, UriComponentsBuilder builder){
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper(
            tags_ids -> {
                //if text_ids == null -> texts = null;
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
                //if type_ids == null -> types = null;

                    Optional<NamedEntityType> _opt = netService.find(_type_id);
                    if(_opt.isPresent()){
                        return _opt.get();
                    }
                    return null;
                    /**If it doesnt find the tag just skips it TODO: updateType*/
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

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isPresent()){
            netService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateType(@PathVariable("id") Long id, @RequestBody PutNamedEntityTypeRequest rq){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        netService.update(PutNamedEntityTypeRequest.dtoToEntityUpdater( 
            tags_ids -> {
                //if text_ids == null -> texts = null;
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
                //if type_ids == null -> types = null;

                    Optional<NamedEntityType> _opt = netService.find(_type_id);
                    if(_opt.isPresent()){
                        return _opt.get();
                    }
                    return null;

                    /**If it doesnt find the tag just skips it TODO: updateType*/
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
        ).apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }
}
