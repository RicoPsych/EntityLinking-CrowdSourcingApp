package project.app.text_tag.ne_type;

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

import project.app.text_tag.ne_type.dto.GetNamedEntityTypeResponse;
import project.app.text_tag.ne_type.dto.PostNamedEntityTypeRequest;
import project.app.text_tag.ne_type.dto.PutNamedEntityTypeRequest;
import project.app.text_tag.text_tag.TextTag;
import project.app.text_tag.text_tag.TextTagService;

@RestController
@RequestMapping("api/netypes")
public class NamedEntityTypeController {
    private NamedEntityTypeService netService;
    private TextTagService textTagService;

    @Autowired
    NamedEntityTypeController(NamedEntityTypeService namedEntityTypeService, TextTagService textTagService){
        this.netService = namedEntityTypeService;
        this.textTagService = textTagService;
    }

    // @GetMapping("{id}")
    // public ResponseEntity<GetNamedEntityTypeResponse> getText(@PathVariable("id") Long id){
    //     Optional<NamedEntityType> opt = netService.find(id);
    //     if (opt.isEmpty()){
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok(GetNamedEntityTypeResponse.entityToDtoMapper().apply(opt.get()));
    // }

    @PostMapping
    public ResponseEntity<Void> postNamedEntityType(@RequestBody PostNamedEntityTypeRequest rq, UriComponentsBuilder builder){
        NamedEntityType type = PostNamedEntityTypeRequest.dtoToEntityMapper(
            textTag_ids -> {
                List<TextTag> textTags = new ArrayList<>();
                for(Long _id : textTag_ids){
                        Optional<TextTag> _opt = textTagService.find(_id);
                        if(_opt.isPresent()){
                            textTags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postNamedEntityType task_set */
                }
                return textTags;
            }
        ).apply(rq);

        type = netService.add(type);

        //UPDATE OWNING SIDE to persist relationship 
        for(TextTag tag : type.getTextTags()){
            List<NamedEntityType> newTypes = tag.getNamedEntityTypes();
            newTypes.add(type);
            tag.setNamedEntityTypes(newTypes);
            //update OWNING side -> update in other microservice
            textTagService.update(tag,false);
        }

        return ResponseEntity
            .created(builder
                .pathSegment("api","netypes","{id}")
                .buildAndExpand(type.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteNamedEntityType(@PathVariable("id") Long id){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Text not found").build();
        }

        //TODO:OPTYMALIZACJA
        //Delete relationships with sets in OWNING SIDE
        for(TextTag tag : textTagService.findByNamedEntityType(opt.get())){
            List<NamedEntityType> newTypes = new ArrayList<>();
            
            for (NamedEntityType type : tag.getNamedEntityTypes()) {
                if(type.getId() != opt.get().getId())
                    newTypes.add(netService.find(type.getId()).get());
            }

            tag.setNamedEntityTypes(newTypes);
            textTagService.update(tag,false);
        }

        netService.delete(opt.get());

        return ResponseEntity.accepted().build();
    }

    
    @PutMapping("{id}")
    public ResponseEntity<Void> updateType(@PathVariable("id") Long id, @RequestBody PutNamedEntityTypeRequest rq){
        Optional<NamedEntityType> opt = netService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Type not found").build();
        }

        //TODO:OPTYMALIZACJA
        //Delete relationships with sets in OWNING SIDE
        for(TextTag tag : textTagService.findByNamedEntityType(opt.get())){
            List<NamedEntityType> newTypes = new ArrayList<>();
            
            for (NamedEntityType type : tag.getNamedEntityTypes()) {
                if(type.getId() != opt.get().getId())
                    newTypes.add(netService.find(type.getId()).get());
            }

            tag.setNamedEntityTypes(newTypes);
            textTagService.update(tag,false);
        }

        //create entity from request
        //TODO: OPTYMALNIEJ MOZNA ZROBIC
        NamedEntityType type = PutNamedEntityTypeRequest.dtoToEntityUpdater( 
            textTag_ids -> {
                List<TextTag> textTags = new ArrayList<>();
                for(Long _id : textTag_ids){
                        Optional<TextTag> _opt = textTagService.find(_id);
                        if(_opt.isPresent()){
                            textTags.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postNamedEntityType task_set */
                }
                return textTags;
            }
        ).apply(opt.get(),rq);

            
        //UPDATE OWNING SIDE to persist relationship 
        for(TextTag tag : type.getTextTags()){
            List<NamedEntityType> newTypes = tag.getNamedEntityTypes();
            newTypes.add(type);
            tag.setNamedEntityTypes(newTypes);
            //update OWNING side -> update in other microservice
            textTagService.update(tag,false);
        }


        return ResponseEntity.accepted().build();
    }
}
