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
import project.app.named_entity.named_entity_type.NamedEntityTypeService;
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
        List<NamedEntity> entities = namedEntityService.findAll();
            return ResponseEntity.ok(GetNamedEntitiesResponse.entityToDtoMapper().apply(entities));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetNamedEntityResponse> getNamedEntity(@PathVariable("id") Long id,@PathVariable("text_id") Long text_id){
        Optional<NamedEntity> opt = namedEntityService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetNamedEntityResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postNamedEntity(@RequestBody PostNamedEntityRequest rq,@PathVariable("text_id") Long text_id ,UriComponentsBuilder builder){
        NamedEntity entity = PostNamedEntityRequest.dtoToEntityMapper(() -> textService.find(text_id).get(), (type_id)-> namedEntityTypeService.find(type_id).get() ) //TODO: TYPE GETTER
            .apply(rq);
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
        if (opt.isPresent()){
            //delete entity
            namedEntityService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateNamedEntity(@RequestBody PutNamedEntityRequest rq,@PathVariable("id") Long id,@PathVariable("text_id") Long text_id){
        Optional<NamedEntity> opt = namedEntityService.find(id);
        if (opt.isPresent()){
            namedEntityService.update(PutNamedEntityRequest.dtoToEntityUpdater(()->textService.find(text_id).get(),(type_id)-> namedEntityTypeService.find(type_id).get())//TODO: TYPE GETTER
            .apply(opt.get(),rq));
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}