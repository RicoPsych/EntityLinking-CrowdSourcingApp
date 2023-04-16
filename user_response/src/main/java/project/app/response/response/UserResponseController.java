package project.app.response.response;


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

import project.app.response.response.dto.*;

@RestController
@RequestMapping("api/response")
public class UserResponseController {

    private UserResponseService userResponseService;

    @Autowired
    public UserResponseController(UserResponseService service){

        this.userResponseService = service;
    }

    @GetMapping
    public ResponseEntity <GetUserResponsesResponse> getUserResponses(){
        return ResponseEntity.ok(GetUserResponsesResponse.entityToDtoMapper().apply(userResponseService.findAll()));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponseResponse> getUserResponse(@PathVariable("id") Long id){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }
        return ResponseEntity.ok(GetUserResponseResponse.entityToDtoMapper().apply(opt.get()));
        
    }

    @PostMapping
    public ResponseEntity<Void> postUserResponce(@RequestBody PostUserResponseRequest rq, UriComponentsBuilder builder){
        Response response = PostUserResponseRequest.dtoToEntityMapper().apply(rq);
        response = userResponseService.add(response);

        return ResponseEntity
            .created(builder
                .pathSegment("api","response","{id}")
                .buildAndExpand(response.getId()).toUri())
            .build();  
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteUserResponse(@PathVariable("id") Long id){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }
        userResponseService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateUserRespose(@PathVariable("id") Long id,@RequestBody PutUserResponseRequest rq){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }

        userResponseService.update(PutUserResponseRequest.dtoToEntityUpdater().apply(opt.get(),rq), true);

        return ResponseEntity.accepted().build();
    }


    
}
