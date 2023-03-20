package project.app.named_entity.named_entity;

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

import project.app.named_entity.named_entity.dto.GetNamedEntitiesResponse;
import project.app.named_entity.named_entity.dto.GetNamedEntityResponse;
import project.app.named_entity.named_entity.dto.PostNamedEntityRequest;
import project.app.named_entity.named_entity.dto.PutNamedEntityRequest;
import project.app.named_entity.named_entity_type.NamedEntityType;
import project.app.named_entity.named_entity_type.NamedEntityTypeService;
import project.app.named_entity.text.Text;
import project.app.named_entity.text.TextService;

@RestController
@RequestMapping("api/texts/{text_id}/entities")
public class NamedEntityController {
    NamedEntityService namedEntityService;
    TextService textService;
    NamedEntityTypeService namedEntityTypeService;

    @Autowired
    NamedEntityController(NamedEntityService service, TextService textService,NamedEntityTypeService namedEntityTypeService){
        this.namedEntityService = service;
        this.textService = textService;
        this.namedEntityTypeService = namedEntityTypeService;
    }

    @GetMapping
    public ResponseEntity<GetNamedEntitiesResponse> getNamedEntities(@PathVariable("text_id") Long text_id){
        Optional<Text> opt = textService.find(text_id);
        if( opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }

        List<NamedEntity> entities = namedEntityService.findByText(opt.get());
        return ResponseEntity.ok(GetNamedEntitiesResponse.entityToDtoMapper().apply(entities));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetNamedEntityResponse> getNamedEntity(@PathVariable("id") Long id,@PathVariable("text_id") Long text_id){
        Optional<NamedEntity> opt = namedEntityService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Named Entity not found").build();
        }
        return ResponseEntity.ok(GetNamedEntityResponse.entityToDtoMapper().apply(opt.get()));          
    }

    @PostMapping
    public ResponseEntity<Void> postNamedEntity(@RequestBody PostNamedEntityRequest rq,@PathVariable("text_id") Long text_id ,UriComponentsBuilder builder){
        Optional<Text> opt = textService.find(text_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }
        
        NamedEntity entity = PostNamedEntityRequest.dtoToEntityMapper(() -> opt.get(), 
        type_id-> {
            Optional<NamedEntityType> _opt = namedEntityTypeService.find(type_id);
            if(_opt.isEmpty()){
                return null;
            }
            return _opt.get();
        }).apply(rq);
            entity = namedEntityService.add(entity);

        return ResponseEntity
            .created(builder
                .pathSegment("api","texts","{text_id}","entities","{id}")
                .buildAndExpand(entity.getText().getId(),entity.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNamedEntity(@PathVariable("id") Long id){
        Optional<NamedEntity> opt = namedEntityService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Entity not found").build();
            
        }
        //delete entity
        namedEntityService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateNamedEntity(@RequestBody PutNamedEntityRequest rq,@PathVariable("id") Long id,@PathVariable("text_id") Long text_id){
        Optional<NamedEntity> opt = namedEntityService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Named Entity not found").build();
        }

        namedEntityService.update(PutNamedEntityRequest.dtoToEntityUpdater(type_id-> {
            Optional<NamedEntityType> _opt = namedEntityTypeService.find(type_id);
            if (_opt.isEmpty()){
                return null;
            }
            return _opt.get();
        }).apply(opt.get(),rq));
        
        return ResponseEntity.accepted().build();

    }
}
