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
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postText(@RequestBody PostTextRequest rq, UriComponentsBuilder builder){
        Text text = PostTextRequest.dtoToEntityMapper()
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

}
