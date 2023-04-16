package project.app.selected_word.selected_word;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import project.app.selected_word.response.Response;
import project.app.selected_word.response.ResponseService;
import project.app.selected_word.selected_word.dto.*;

@RestController
@RequestMapping("api/response/{response_id}/selected_word")
public class SelectedWordController {

    private SelectedWordService selectedWordService;

    private ResponseService responseService;

    @Autowired
    public SelectedWordController(SelectedWordService service, ResponseService responseService){

        this.selectedWordService = service;
        this.responseService = responseService;
    }

    @GetMapping
    public ResponseEntity <GetSelectedWordsResponse> getSelectedWords(@PathVariable("response_id") Long response_id){
        Optional<Response> opt = responseService.find(response_id);
        if( opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }

        List<SelectedWord> selectedWords = selectedWordService.findByResponse(opt.get());
        
        return ResponseEntity.ok(GetSelectedWordsResponse.entityToDtoMapper().apply(selectedWords));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetSelectedWordResponse> getSelectedWord(@PathVariable("id") Long id, @PathVariable("response_id") Long response_id){
        Optional<SelectedWord> opt = selectedWordService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "SelectedWord not found").build();
        }
        return ResponseEntity.ok(GetSelectedWordResponse.entityToDtoMapper().apply(opt.get()));
    }

    @PostMapping
    public ResponseEntity<Void> postSelectedWord(@RequestBody PostSelectedWordRequest rq,@PathVariable("response_id") Long response_id, UriComponentsBuilder builder){

        Optional<Response> opt = responseService.find(response_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }
            
        SelectedWord selected_word = PostSelectedWordRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        selected_word = selectedWordService.add(selected_word);
        return ResponseEntity
            .created(builder
                .pathSegment("api","response","{response_id}","seleceted_word", "{id}")
                .buildAndExpand(selected_word.getResponse().getId(),selected_word.getId()).toUri())
            .build(); 
    
    
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSelectedWord(@PathVariable("id") Long id){
        Optional<SelectedWord> opt = selectedWordService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "SelectedWord not found").build();
        }
        selectedWordService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateSelectedWord(@PathVariable("id") Long id,@RequestBody PutSelectedWordRequest rq){
        Optional<SelectedWord> opt = selectedWordService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "SelectedWord not found").build();
        }

        selectedWordService.update(PutSelectedWordRequest.dtoToEntityUpdater().apply(opt.get(),rq), true);

        return ResponseEntity.accepted().build();
    }


    
}

