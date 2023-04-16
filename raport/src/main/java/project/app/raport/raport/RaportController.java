package project.app.raport.raport;

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

import project.app.raport.raport.dto.*;
import project.app.raport.response.Response;
import project.app.raport.response.ResponseService;

@RestController
@RequestMapping("api/response/{response_id}/raport")
public class RaportController {

    private RaportService raportService;
    private ResponseService responseService;

    @Autowired
    public RaportController(RaportService service, ResponseService responseService){

        this.raportService = service;
        this.responseService = responseService;
    }

    @GetMapping
    public ResponseEntity <GetRaportsResponse> getRaports(@PathVariable("response_id") Long response_id){

        Optional<Response> opt = responseService.find(response_id);
        if( opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }

        List<Raport> raports = raportService.findByResponse(opt.get());
        
        return ResponseEntity.ok(GetRaportsResponse.entityToDtoMapper().apply(raports));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetRaportResponse> getRaport(@PathVariable("id") Long id, @PathVariable("response_id") Long response_id){
        Optional<Raport> opt = raportService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Raport not found").build();
        }
        return ResponseEntity.ok(GetRaportResponse.entityToDtoMapper().apply(opt.get()));
        
    }

    @PostMapping
    public ResponseEntity<Void> postRaport(@RequestBody PostRaportRequest rq,@PathVariable("response_id") Long response_id, UriComponentsBuilder builder){

        Optional<Response> opt = responseService.find(response_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Response not found").build();
        }
            
        Raport raport = PostRaportRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        raport = raportService.add(raport);
        return ResponseEntity
            .created(builder
                .pathSegment("api","response","{response_id}","raport", "{id}")
                .buildAndExpand(raport.getResponse().getId(),raport.getId()).toUri())
            .build(); 
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteRaport(@PathVariable("id") Long id){
        Optional<Raport> opt = raportService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Raport not found").build();
        }
        raportService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateRaport(@PathVariable("id") Long id,@RequestBody PutRaportRequest rq){
        Optional<Raport> opt = raportService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Raport not found").build();
        }

        raportService.update(PutRaportRequest.dtoToEntityUpdater().apply(opt.get(),rq), true);

        return ResponseEntity.accepted().build();
    }
    
}
