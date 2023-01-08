package project.app.text_tag.text_tag;

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

import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.ne_type.NamedEntityTypeService;
import project.app.text_tag.text.TextController;
import project.app.text_tag.text.TextService;
import project.app.text_tag.text_tag.dto.*;

@RestController
@RequestMapping("api/tags")
public class TextTagController {
    private TextTagService textTagService;
    private TextService textService;
    private NamedEntityTypeService namedEntityTypeService;

    @Autowired
    public TextTagController(TextTagService service){
        textTagService = service;
        
    }

    @GetMapping
    public ResponseEntity<GetTextTagsResponse> getTextTags(){
        return ResponseEntity.ok(GetTextTagsResponse.entityToDtoMapper().apply(textTagService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTextTagResponse> getTextTag(@PathVariable("id") Long id){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetTextTagResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postTextTag(@RequestBody PostTextTagRequest rq, UriComponentsBuilder builder){
        TextTag textTag = PostTextTagRequest.dtoToEntityMapper() //restrictions?
            .apply(rq);
        textTag = textTagService.add(textTag);
        return ResponseEntity
            .created(builder
                .pathSegment("api","texts","{id}")
                .buildAndExpand(textTag.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTextTag(@PathVariable("id") Long id){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isPresent()){
            textTagService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTextTag(@PathVariable("id") Long id,@RequestBody PutTextTagRequest rq){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isPresent()){
            textTagService.update(PutTextTagRequest.dtoToEntityUpdater().apply(opt.get(),rq));
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
