package project.app.task_set.ne_type;

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

import project.app.task_set.ne_type.dto.GetNamedEntityTypeResponse;
import project.app.task_set.ne_type.dto.PostNamedEntityTypeRequest;
import project.app.task_set.ne_type.dto.PutNamedEntityTypeRequest;
import project.app.task_set.task_set.TaskSet;
import project.app.task_set.task_set.TaskSetService;

@RestController
@RequestMapping("api/netypes")
public class NamedEntityTypeController {
    NamedEntityTypeService netService;
    TaskSetService taskSetService;
    /**
     * Autowired constructor for NamedEntityType representation Controller
     * @param service NamedEntityType service
     */
    @Autowired
    NamedEntityTypeController(NamedEntityTypeService service,TaskSetService taskSetService){
        this.netService = service;
        this.taskSetService = taskSetService;
    }
    //TODO:DEBUG
    // @GetMapping("{id}")
    // public ResponseEntity<GetNamedEntityTypeResponse> getNamedEntityType(@PathVariable("id") Long id){
    //     Optional<NamedEntityType> opt = netService.find(id);
    //     if (opt.isPresent()){
    //         return ResponseEntity.ok(GetNamedEntityTypeResponse.entityToDtoMapper().apply(opt.get()));
    //     }
    //     else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping
    public ResponseEntity<Void> postNamedEntityType(@RequestBody PostNamedEntityTypeRequest rq, UriComponentsBuilder builder){
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper(
            taskSet_ids -> {
                List<TaskSet> taskSets = new ArrayList<>();
                for(Long _id : taskSet_ids){
                        Optional<TaskSet> _opt = taskSetService.find(_id);
                        if(_opt.isPresent()){
                            taskSets.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postNamedEntityType task_set */
                }
                return taskSets;
            }).apply(rq); 

        type = netService.add(type);

        //UPDATE OWNING SIDE to persist relationship 
        for(TaskSet set : type.getTaskSets()){
            List<NamedEntityType> newTypes = set.getNamedEntityTypes();
            newTypes.add(type);
            set.setNamedEntityTypes(newTypes);
            taskSetService.update(set,false);
        }

        return ResponseEntity
            .created(builder
                .pathSegment("api","netypes","{id}")
                .buildAndExpand(type.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNamedEntityType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Type not found").build();
        }

        //Delete relationships with sets in OWNING SIDE
        for(TaskSet set : taskSetService.findByNamedEntityType(opt.get())){
            List<NamedEntityType> newTypes = new ArrayList<>();
            
            for (NamedEntityType type : set.getNamedEntityTypes()) {
                if(type.getId() != opt.get().getId())
                    newTypes.add(netService.find(type.getId()).get());
            }

            set.setNamedEntityTypes(newTypes);
            taskSetService.update(set,false);
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

        //Delete relationships with sets in OWNING SIDE
        for(TaskSet set : taskSetService.findByNamedEntityType(opt.get())){
            List<NamedEntityType> newTypes = new ArrayList<>();
            
            for (NamedEntityType type : set.getNamedEntityTypes()) {
                if(type.getId() != opt.get().getId())
                    newTypes.add(netService.find(type.getId()).get());
            }

            set.setNamedEntityTypes(newTypes);
            taskSetService.update(set,false);
        }

        //TODO: OPTYMALNIEJ MOZNA ZROBIC
        NamedEntityType type = PutNamedEntityTypeRequest.dtoToEntityUpdater( 
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
        ).apply(opt.get(),rq);

            
        //UPDATE OWNING SIDE to persist relationship 
        for(TaskSet set : type.getTaskSets()){
            List<NamedEntityType> newTypes = set.getNamedEntityTypes();
            newTypes.add(type);
            set.setNamedEntityTypes(newTypes);
            taskSetService.update(set,false);
        }

        return ResponseEntity.accepted().build();
    }
}
