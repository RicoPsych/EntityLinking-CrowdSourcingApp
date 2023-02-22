package project.app.text.text;

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

import project.app.text.text.dto.*;
import project.app.text.text_tag.TextTag;
import project.app.text.text_tag.TextTagService;

@RestController
@RequestMapping("api/texts")
public class TextController {
    private TextService textService;
    private TextTagService tagService;
    

    @Autowired
    public TextController(TextService service,TextTagService tagService){
        this.textService = service;
        this.tagService = tagService;

    }

    @GetMapping
    public ResponseEntity<GetTextsResponse> getTexts(){
        return ResponseEntity.ok(GetTextsResponse.entityToDtoMapper().apply(textService.findAll()));
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
        Text text = PostTextRequest.dtoToEntityMapper( 
            tag_ids -> {
            //if tag_ids == null -> tags = null;
            List<TextTag> tags = new ArrayList<>();
            for(Long id : tag_ids){
                    Optional<TextTag> opt = tagService.find(id);
                    if(opt.isPresent()){
                        tags.add(opt.get());
                    }
                    /**If it doesnt find the tag just skips it TODO: */
            }
            return tags;
        })
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

    @PutMapping("{id}")
    public ResponseEntity<Void> updateText(@PathVariable("id") Long id,@RequestBody PutTextRequest rq){
        Optional<Text> opt = textService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        textService.update(PutTextRequest.dtoToEntityUpdater(
            tag_ids -> {
                //if tag_ids == null -> tags = null;
                List<TextTag> tags = new ArrayList<>();
                for(Long _id : tag_ids){
                    Optional<TextTag> _opt = tagService.find(_id);
                    if(_opt.isPresent()){
                        tags.add(_opt.get());
                    }
                    /**If it doesnt find the tag just skips it TODO: brak info o niedodanych tagach*/
                }
                return tags;
            }).apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }
}
