package project.app.named_entity.named_entity_type;

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

import project.app.named_entity.named_entity_type.dto.GetNamedEntityTypeResponse;
import project.app.named_entity.named_entity_type.dto.PostNamedEntityTypeRequest;


@RestController
@RequestMapping("api/types")
public class NamedEntityTypeController {
    private NamedEntityTypeService service;
    

    @Autowired
    public NamedEntityTypeController(NamedEntityTypeService service){
        this.service = service;

    }

    @GetMapping("{id}")
    public ResponseEntity<GetNamedEntityTypeResponse> getType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = service.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetNamedEntityTypeResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postType(@RequestBody PostNamedEntityTypeRequest rq, UriComponentsBuilder builder){
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper()
        .apply(rq);
        type = service.add(type);
        return ResponseEntity
            .created(builder
                .pathSegment("api","types","{id}")
                .buildAndExpand(type.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = service.find(id);
        if (opt.isPresent()){
            service.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
