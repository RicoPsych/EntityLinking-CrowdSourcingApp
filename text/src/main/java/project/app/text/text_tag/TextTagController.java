package project.app.text.text_tag;

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

import project.app.text.text.Text;
import project.app.text.text.TextService;
import project.app.text.text_tag.dto.GetTextTagResponse;
import project.app.text.text_tag.dto.PostTextTagRequest;
import project.app.text.text_tag.dto.PutTextTagRequest;


@RestController
@RequestMapping("api/tags")
public class TextTagController {
    private TextTagService textTagService;
    private TextService textService;

    @Autowired
    public TextTagController(TextTagService service, TextService textService){
        this.textTagService = service;
        this.textService = textService;
    }

    // @GetMapping("{id}")
    // public ResponseEntity<GetTextTagResponse> getTextTag(@PathVariable("id") Long id){
    //     Optional<TextTag> opt = textTagService.find(id);
    //     if (opt.isEmpty()){
    //         return ResponseEntity.notFound().header("Description", "TextTag not found").build();
    //     }
    //     return ResponseEntity.ok(GetTextTagResponse.entityToDtoMapper().apply(opt.get()));
    // }

    @PostMapping
    public ResponseEntity<Void> postTextTag(@RequestBody PostTextTagRequest rq, UriComponentsBuilder builder){
        //TODO: restricitions??
        TextTag textTag = PostTextTagRequest.dtoToEntityMapper(
            text_ids -> {
                List<Text> texts = new ArrayList<>();
                for(Long _id : text_ids){
                        Optional<Text> _opt = textService.find(_id);
                        if(_opt.isPresent()){
                            texts.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO: updateTaskSet*/
                }
                return texts;
            }
        ).apply(rq);
        
        textTag = textTagService.add(textTag);

        //UPDATE OWNING SIDE to persist relationship 
        for(Text text : textTag.getTexts()){
            List<TextTag> newTextTags = text.getTextTags();
            newTextTags.add(textTag);
            text.setTextTags(newTextTags);
            textService.update(text, false);
        }

        return ResponseEntity
            .created(builder
                .pathSegment("api","tags","{id}")
                .buildAndExpand(textTag.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTextTag(@PathVariable("id") Long id){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text Tag not found").build();
        }
        //delete connection to the tags
        for(Text text : textService.findByTextTag(opt.get())){                
            List<TextTag> newTags = new ArrayList<>();

            //DELETE tag
            for (TextTag tag : text.getTextTags()) {
                if(tag.getId() != opt.get().getId())
                    newTags.add(textTagService.find(tag.getId()).get());
            }

            //UPDATE TEXT WITH NEW TAGS
            text.setTextTags(newTags);
            textService.update(text,false);
        }
        //delete tag
        textTagService.delete(opt.get());
        return ResponseEntity.accepted().build();

    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTextTag(@PathVariable("id") Long id, @RequestBody PutTextTagRequest rq){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text Tag not found").build();
        }


        //delete connection to the tags
        //TODO: OPTYMALNIEJ MOZNA ZROBIC
        for(Text text : textService.findByTextTag(opt.get())){                
            List<TextTag> newTags = new ArrayList<>();

            //DELETE tag
            for (TextTag tag : text.getTextTags()) {
                if(tag.getId() != opt.get().getId())
                    newTags.add(textTagService.find(tag.getId()).get());
            }

            //UPDATE TEXT WITH NEW TAGS
            text.setTextTags(newTags);
            textService.update(text,false);
        }

        //update entity from request
        //TODO: OPTYMALNIEJ MOZNA ZROBIC
        TextTag textTag = PutTextTagRequest.dtoToEntityUpdater(
            text_ids -> {
                List<Text> texts = new ArrayList<>();
                for(Long _id : text_ids){
                        Optional<Text> _opt = textService.find(_id);
                        if(_opt.isPresent()){
                            texts.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO: updateTaskSet*/
                }
                return texts;
            }
        )
        .apply(opt.get(),rq);

        //UPDATE OWNING SIDE to persist relationship 
        for(Text text : textTag.getTexts()){
            List<TextTag> newTextTags = text.getTextTags();
            newTextTags.add(textTag);
            text.setTextTags(newTextTags);
            textService.update(text, false);
        }

        return ResponseEntity.accepted().build();
    }

}
