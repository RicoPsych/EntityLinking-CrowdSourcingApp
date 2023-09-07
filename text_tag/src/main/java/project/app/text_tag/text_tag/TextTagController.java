package project.app.text_tag.text_tag;

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

import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.ne_type.NamedEntityTypeService;
import project.app.text_tag.text.Text;
import project.app.text_tag.text.TextService;
import project.app.text_tag.text_tag.dto.*;

@RestController
@RequestMapping("api/tags")
public class TextTagController {
    private TextTagService textTagService;
    private TextService textService;
    private NamedEntityTypeService namedEntityTypeService;

    @Autowired
    public TextTagController(TextTagService service,TextService textService,NamedEntityTypeService namedEntityTypeService){
        this.textTagService = service;
        this.textService = textService;
        this.namedEntityTypeService = namedEntityTypeService;
    }

    @GetMapping
    public ResponseEntity<GetTextTagsResponse> getTextTags(){
        return ResponseEntity.ok(GetTextTagsResponse.entityToDtoMapper().apply(textTagService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTextTagResponse> getTextTag(@PathVariable("id") Long id){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Tag not found").build();

        }
        return ResponseEntity.ok(GetTextTagResponse.entityToDtoMapper().apply(opt.get()));

    }

    @PostMapping
    public ResponseEntity<Void> postTextTag(@RequestBody PostTextTagRequest rq, UriComponentsBuilder builder){
        TextTag textTag = PostTextTagRequest.dtoToEntityMapper(
            types_ids -> {
                List<NamedEntityType> types = new ArrayList<>();
                for(Long _id : types_ids){
                        Optional<NamedEntityType> _opt = namedEntityTypeService.find(_id);
                        if(_opt.isPresent()){
                            types.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return types;
            },
            texts_ids -> {
                List<Text> texts = new ArrayList<>();
                for(Long _id : texts_ids){
                        Optional<Text> _opt = textService.find(_id);
                        if(_opt.isPresent()){
                            texts.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return texts;
            }
        ).apply(rq);
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
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Tag not found").build();
        }
        textTagService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTextTag(@PathVariable("id") Long id,@RequestBody PutTextTagRequest rq){
        Optional<TextTag> opt = textTagService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Tag not found").build();
        }
        textTagService.update(PutTextTagRequest.dtoToEntityUpdater(
            types_ids -> {
                List<NamedEntityType> types = new ArrayList<>();
                for(Long _id : types_ids){
                        Optional<NamedEntityType> _opt = namedEntityTypeService.find(_id);
                        if(_opt.isPresent()){
                            types.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return types;
            },
            texts_ids -> {
                List<Text> texts = new ArrayList<>();
                for(Long _id : texts_ids){
                        Optional<Text> _opt = textService.find(_id);
                        if(_opt.isPresent()){
                            texts.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:UpdateType */
                }
                return texts;
            }
        ).apply(opt.get(),rq),true);
        return ResponseEntity.accepted().build();
    }
}
