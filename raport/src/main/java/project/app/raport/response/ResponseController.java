package project.app.raport.response;

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

import project.app.raport.response.dto.*;

@RestController
@RequestMapping("api/response")
public class ResponseController {

    private ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService service){
        this.responseService = service;

    }

    @GetMapping("{id}")
    public ResponseEntity<GetResponseResponse> getResponse(@PathVariable("id") Long id){
        Optional<Response> opt = responseService.find(id);
        if (opt.isPresent()){
            return ResponseEntity.ok(GetResponseResponse.entityToDtoMapper().apply(opt.get()));
        }
        else{
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> postResponse(@RequestBody PostResponseRequest rq, UriComponentsBuilder builder){
        Response response = PostResponseRequest.dtoToEntityMapper().apply(rq);
        response = responseService.add(response);
        return ResponseEntity
            .created(builder
                .pathSegment("api","response","{id}")
                .buildAndExpand(response.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteResponse(@PathVariable("id") Long id){
        Optional<Response> opt = responseService.find(id);
        if (opt.isPresent()){
            responseService.delete(opt.get());
            return ResponseEntity.accepted().build();
        }
        else{
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }
    }

}