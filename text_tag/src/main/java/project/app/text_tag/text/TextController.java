package project.app.text_tag.text;

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

import project.app.text_tag.text.dto.*;
import project.app.text_tag.text_tag.TextTag;
import project.app.text_tag.text_tag.TextTagService;

@RestController
@RequestMapping("api/texts")
public class TextController {
    private TextService textService;
    private TextTagService tagService;

    @Autowired
    public TextController(TextService textService,TextTagService tagService){
        this.textService = textService;
        this.tagService = tagService;
    }

    // @GetMapping("{id}")
    // public ResponseEntity<GetTextResponse> getText(@PathVariable("id") Long id){
    //     Optional<Text> opt = textService.find(id);
    //     if (opt.isPresent()){
    //         return ResponseEntity.ok(GetTextResponse.entityToDtoMapper().apply(opt.get()));
    //     }
    //     else{
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    @PostMapping
    public ResponseEntity<Void> postText(@RequestBody PostTextRequest rq, UriComponentsBuilder builder){
        Text text = PostTextRequest.dtoToEntityMapper(
            textTags_ids -> {
                List<TextTag> textTags = new ArrayList<>();
                for(Long _id : textTags_ids){
                        Optional<TextTag> _opt = tagService.find(_id);
                        if(_opt.isPresent()){
                            textTags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postNamedEntityType task_set */
                }
                return textTags;
            }
        ).apply(rq);


        text = textService.add(text);

        //UPDATE OWNING SIDE to persist relationship 
        for(TextTag tag : text.getTextTags()){
            List<Text> newTexts = tag.getTexts();
            newTexts.add(text);
            tag.setTexts(newTexts);
            tagService.update(tag,false);
        }

        return ResponseEntity
            .created(builder
                .pathSegment("api","texts","{id}")
                .buildAndExpand(text.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteText(@PathVariable("id") Long id){
        Optional<Text> opt = textService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }

        for(TextTag tag : tagService.findByText(opt.get())){
            List<Text> newTexts = new ArrayList<>();

            for(Text text : tag.getTexts() ){
                if(text.getId() != opt.get().getId())
                    newTexts.add(textService.find(text.getId()).get());
            }

            tag.setTexts(newTexts);
            tagService.update(tag, false);
        }

        textService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateText(@PathVariable("id") Long id, @RequestBody PutTextRequest rq){
        Optional<Text> opt = textService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }

        //DELETE relationships
        for(TextTag tag : tagService.findByText(opt.get())){
            List<Text> newTexts = new ArrayList<>();

            for(Text text : tag.getTexts() ){
                if(text.getId() != opt.get().getId())
                    newTexts.add(textService.find(text.getId()).get());
            }

            tag.setTexts(newTexts);
            tagService.update(tag, false);
        }

        //create entity from request
        Text text = PutTextRequest.dtoToEntityMapper(
            textTags_ids -> {
                List<TextTag> textTags = new ArrayList<>();
                for(Long _id : textTags_ids){
                        Optional<TextTag> _opt = tagService.find(_id);
                        if(_opt.isPresent()){
                            textTags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postNamedEntityType task_set */
                }
                return textTags;
            }
        ).apply(opt.get(),rq);

        //UPDATE OWNING SIDE to persist relationship 
        for(TextTag tag : text.getTextTags()){
            List<Text> newTexts = tag.getTexts();
            newTexts.add(text);
            tag.setTexts(newTexts);
            tagService.update(tag,false);
        }

        return ResponseEntity.accepted().build();
    }

}
