package project.app.response.response;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import project.app.response.response.dto.*;
import project.app.response.task.*;

@RestController
@RequestMapping("api/tasks/{task_id}/response")
public class UserResponseController {

    private UserResponseService userResponseService;

    private TaskService taskService;

    @Autowired
    public UserResponseController(UserResponseService service, TaskService taskService){

        this.userResponseService = service;
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity <GetUserResponsesResponse> getUserResponses(@PathVariable("task_id") Long task_id){

        Optional<Task> opt = taskService.find(task_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        List<Response> responses = userResponseService.findByTask(opt.get());
        
        return ResponseEntity.ok(GetUserResponsesResponse.entityToDtoMapper().apply(responses));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetUserResponseResponse> getUserResponse(@PathVariable("id") Long id, @PathVariable("task_id") Long task_id){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }
        return ResponseEntity.ok(GetUserResponseResponse.entityToDtoMapper().apply(opt.get()));
        
    }

    @PostMapping
    public ResponseEntity<Void> postUserResponce(@RequestBody PostUserResponseRequest rq, @PathVariable("task_id") Long task_id, UriComponentsBuilder builder){


        Optional<Task> opt = taskService.find(task_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
            
        Response response = PostUserResponseRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);

        response = userResponseService.add(response);

        return ResponseEntity
            .created(builder
                .pathSegment("api","task","{task_id}","response", "{id}")
                .buildAndExpand(response.getTask().getId(),response.getId()).toUri())
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
    public ResponseEntity<Void> updateUserRespose(@PathVariable("id") Long id, @RequestBody PutUserResponseRequest rq){
        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }

        userResponseService.update(PutUserResponseRequest.dtoToEntityUpdater().apply(opt.get(),rq), true);

        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}/validate")
    public ResponseEntity<String> validateResponse(@PathVariable("id") Long id){

        Optional<Response> opt = userResponseService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "UserResponse not found").build();
        }

        long validated_response_id = opt.get().getId();
        Task task = opt.get().getTask();
        List<Response> responses = userResponseService.findByTask(task);

        HashMap<String, Integer> selected_words_counted = new HashMap<String,Integer>();    // ("index_start-index_end", occurances)
        HashSet<String> current_response_selected_words = new HashSet<String>(); 

        for (Response res: responses) {

            // if (res.getId() == validated_response_id) continue;     // skip self    // actually maybe dont XD l8r

            String res_id = String.valueOf(res.getId());
            URI uri;
            try {
                uri = new URI("http://localhost:8089/api/response/" + res_id + "/selected_word");
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class); 

                ObjectMapper mapper = new ObjectMapper();
                long index_start = -1;
                long index_end = -1;                

                try {
                    JsonNode root = mapper.readTree(responseEntity.getBody());
                    JsonNode content_node = root.path("selected_words");

                    for (JsonNode jsonNode : content_node) {

                        index_start = jsonNode.get("index_start").asLong();        
                        index_end = jsonNode.get("index_end").asLong();

                        // add to hashmap
                        String sw_key = String.valueOf(index_start) + "-" +  String.valueOf(index_end);
                        System.out.println(sw_key + "\n");

                        if (res.getId() == validated_response_id) {
                            current_response_selected_words.add(sw_key);
                        } else {
                            // check if sw already exists
                            Integer counter = selected_words_counted.get(sw_key);
                            if (counter != null) {                  
                                selected_words_counted.put(sw_key, counter+1);
                            } else {
                                selected_words_counted.put(sw_key, 1);
                            }
                        }

                    }    

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }       
        
        System.out.println(selected_words_counted);

        int points = 0; //each maching selected points gives a point

        for (String sw : current_response_selected_words) {

            Integer counter = selected_words_counted.get(sw);

            if (counter != null) {    
                if (counter.floatValue() / (responses.size()-1.0) >= 0.5) points++;
            }
        }

        System.out.println("Punkty: " + String.valueOf(points) + " / " + String.valueOf( current_response_selected_words.size()) + " możliwych ");

        return ResponseEntity.status(HttpStatus.OK).body("test");
    }


}
