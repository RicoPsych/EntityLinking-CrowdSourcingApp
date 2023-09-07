package project.app.named_entity.text;

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

import project.app.named_entity.text.dto.*;

@RestController
@RequestMapping("api/texts")
public class TextController {
    private TextService textService;

    @Autowired
    public TextController(TextService service){
        this.textService = service;

    }

    @GetMapping("{id}")
    public ResponseEntity<GetTextResponse> getText(@PathVariable("id") Long id){
        Optional<Text> opt = textService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetTextResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postText(@RequestBody PostTextRequest rq, UriComponentsBuilder builder){
        Text text = PostTextRequest.dtoToEntityMapper(
            // TODO: BEZ WSTAWIANIA TEKSTU Z PODŁĄCZONYMI ENCJAMI
            // entities_ids -> {
            //     //if type_ids == null -> types = null;
            //     List<NamedEntity> entities = new ArrayList<>();
            //     for(Long _id : entities_ids){
            //             Optional<NamedEntity> _opt = entityService.find(_id);
            //             if(_opt.isPresent()){
            //                 entities.add(_opt.get());
            //             }
            //             /**If it doesnt find the tag just skips it TODO:postTaskSet */
            //     }
            //     return entities;
            // }
        ).apply(rq);
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
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }
    }

    //TODO: chyba nie potrzebne

    @PutMapping("{id}")
    public ResponseEntity<Void> putText(@PathVariable("id") Long id, @RequestBody PutTextRequest rq, UriComponentsBuilder builder){
        return ResponseEntity.accepted().build();  
    }


    // @PutMapping("{id}")
    // public ResponseEntity<Void> putText(@PathVariable("id") Long id, @RequestBody PutTextRequest rq, UriComponentsBuilder builder){
    //     Optional<Text> opt = textService.find(id);
    //     if (opt.isEmpty()){
    //         return ResponseEntity.notFound().build();
    //     }

    //     textService.update(PutTextRequest.dtoToEntityUpdater(
    //         entities_ids -> {
    //             //if type_ids == null -> types = null;
    //             List<NamedEntity> entities = new ArrayList<>();
    //             for(Long _id : entities_ids){
    //                     Optional<NamedEntity> _opt = entityService.find(_id);
    //                     if(_opt.isPresent()){
    //                         entities.add(_opt.get());
    //                     }
    //                     /**If it doesnt find the tag just skips it TODO:postTaskSet */
    //             }
    //             return entities;
    //         }).apply(opt.get(), rq));


    //     return ResponseEntity.accepted().build();  
    // }
}
