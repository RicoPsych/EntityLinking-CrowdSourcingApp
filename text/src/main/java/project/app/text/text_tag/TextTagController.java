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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import project.app.text.text.Text;
import project.app.text.text.TextService;
import project.app.text.text_tag.dto.GetTextTagResponse;
import project.app.text.text_tag.dto.PostTextTagRequest;


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
                .pathSegment("api","tags","{id}")
                .buildAndExpand(textTag.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTextTag(@PathVariable("id") Long id){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isPresent()){
            //delete connection to the tag
            for(Text text : textService.findByTag(opt.get())){                
                List<TextTag> new_tags = new ArrayList<>();
                for (TextTag tag : text.getTextTags()) {
                    if(tag.getId() != opt.get().getId())
                        new_tags.add(textTagService.find(tag.getId()).get());
                }

                text.setTextTags(new_tags);
                textService.update(text);
            }
            //delete tag
            textTagService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
