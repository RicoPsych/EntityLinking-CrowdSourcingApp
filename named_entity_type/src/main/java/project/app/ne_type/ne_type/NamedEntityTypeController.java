package project.app.ne_type.ne_type;

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

import project.app.ne_type.ne_type.dto.GetNamedEntityTypeResponse;
import project.app.ne_type.ne_type.dto.PostNamedEntityTypeRequest;

@RestController
@RequestMapping("api/netypes")
public class NamedEntityTypeController {
    NamedEntityTypeService netService;

    @Autowired
    NamedEntityTypeController(NamedEntityTypeService service){
        this.netService = service;
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
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper() //restrictions?
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
    public ResponseEntity<Void> updateType
}
