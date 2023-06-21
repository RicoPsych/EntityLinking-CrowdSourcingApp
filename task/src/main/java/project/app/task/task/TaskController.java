package project.app.task.task;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpHeaders;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task.task.dto.GetTaskResponse;
import project.app.task.task.dto.GetTasksResponse;
import project.app.task.task.dto.PostTaskRequest;
import project.app.task.task.dto.PostTasksRequest;
import project.app.task.task.dto.PutTaskRequest;
import project.app.task.task_set.TaskSet;
import project.app.task.task_set.TaskSetService;

@RestController
@RequestMapping("api/task_sets/{task_set_id}/tasks")
public class TaskController {
    TaskService taskService;
    TaskSetService taskSetService;

    @Autowired
    TaskController(TaskService taskService,TaskSetService taskSetService){
        this.taskService = taskService;
        this.taskSetService = taskSetService;        
    }

    @GetMapping
    public ResponseEntity<GetTasksResponse> getTasks(@PathVariable("task_set_id") Long TaskSetId){
        Optional<TaskSet> opt = taskSetService.find(TaskSetId);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }

        List<Task> tasks = taskService.findByTaskSet(opt.get());
        return ResponseEntity.ok(GetTasksResponse.entityToDtoMapper().apply(tasks));
    }

    @GetMapping("{id}")
    public ResponseEntity<GetTaskResponse> getNamedEntity(@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){    
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }
        return ResponseEntity.ok(GetTaskResponse.entityToDtoMapper().apply(opt.get()));
    }

    @PostMapping
    public ResponseEntity<Void> postTask(@RequestBody PostTaskRequest rq, @PathVariable("task_set_id") Long task_set_id , UriComponentsBuilder builder){
        Optional<TaskSet> opt = taskSetService.find(task_set_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet Not found").build();
        }

        Task task = PostTaskRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        task = taskService.add(task);

        return ResponseEntity
        .created(builder
            .pathSegment("api","task_sets","{task_set_id}","tasks","{id}")
            .buildAndExpand(task.getTaskSet().getId(),task.getId()).toUri())
        .build();  
    }

    @PostMapping("bulk")
    public ResponseEntity<Void> postTasks(@RequestBody PostTasksRequest rq, @PathVariable("task_set_id") Long task_set_id , UriComponentsBuilder builder){
        Optional<TaskSet> opt = taskSetService.find(task_set_id);
        if(opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet Not found").build();
        }

        List<Task> tasks = PostTasksRequest.dtoToEntityMapper(() -> opt.get()).apply(rq);
        for(int i = 0; i< tasks.size() ; i++){
            taskService.add(tasks.get(i));
        }
        return ResponseEntity
        .created(builder
            .pathSegment("api","task_sets","{task_set_id}","tasks","{first_id}-{last_id}")
            .buildAndExpand(task_set_id,tasks.get(0).getId(),tasks.get(tasks.size()-1).getId())
            .toUri())
        .build();  
    }


    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        taskService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> updateTask(@RequestBody PutTaskRequest rq,@PathVariable("id") Long id,@PathVariable("task_set_id") Long task_set){
        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        taskService.update(PutTaskRequest.dtoToEntityMapper( _task_set_id -> {
            Optional<TaskSet> _opt = taskSetService.find(_task_set_id);
            if(_opt.isEmpty())
            {
                return null;
            }
            return _opt.get();
        }).apply(opt.get(),rq));

        return ResponseEntity.accepted().build();
    }



    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _Response{
        private Long id;
        private boolean validity;
    }

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _SelectedWord{
        private Long index_start;
        private Long index_end;
    }


    private List<_Response> getValidResponses(long task_id) {

        URI uri;
        List<_Response> valid_responses = new ArrayList<_Response>();
        try {
            uri = new URI("http://localhost:8087/api/tasks/" + task_id + "/response");
            RestTemplate restTemplate = new RestTemplate();                
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            long res_id = -1;
            boolean res_validity = false;

            try {
                    JsonNode root = mapper.readTree(responseEntity.getBody());
                    JsonNode content_node = root.path("user_responses");

                    for (JsonNode jsonNode : content_node) {

                        res_id = jsonNode.get("id").asLong();        
                        res_validity = jsonNode.get("validity").asBoolean();

                        if (res_validity == true){
                            _Response res = new _Response(res_id, res_validity);
                            valid_responses.add(res);

                            String sw_key = String.valueOf(res_id) + "-" +  String.valueOf(res_validity);
                            System.out.println(sw_key + "\n");
                        }

                    }    

            } catch (Exception e) {
                    e.printStackTrace();
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return valid_responses;
    }

    private List<_Response> getUnvalidResponses(long task_id) {

        URI uri;
        List<_Response> valid_responses = new ArrayList<_Response>();
        try {
            uri = new URI("http://localhost:8087/api/tasks/" + task_id + "/response");
            RestTemplate restTemplate = new RestTemplate();                
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class);

            ObjectMapper mapper = new ObjectMapper();
            long res_id = -1;
            boolean res_validity = false;

            try {
                    JsonNode root = mapper.readTree(responseEntity.getBody());
                    JsonNode content_node = root.path("user_responses");

                    for (JsonNode jsonNode : content_node) {

                        res_id = jsonNode.get("id").asLong();        
                        res_validity = jsonNode.get("validity").asBoolean();

                        if (res_validity == false){
                            _Response res = new _Response(res_id, res_validity);
                            valid_responses.add(res);

                            String sw_key = String.valueOf(res_id) + "-" +  String.valueOf(res_validity);
                            System.out.println(sw_key + "\n");
                        }

                    }    

            } catch (Exception e) {
                    e.printStackTrace();
            }


        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return valid_responses;
    }


    private HashMap<_SelectedWord, Integer> getValidSelectedWordsCounted(List<_Response> valid_responses){

        HashMap<_SelectedWord, Integer> valid_selected_words_counted = new HashMap<_SelectedWord,Integer>();

        for (_Response res: valid_responses) {

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

                        _SelectedWord sw = new _SelectedWord(index_start, index_end);
                        // add to hashmap
                        // check if sw already exists
                        Integer counter = valid_selected_words_counted.get(sw);
                        if (counter != null) {                  
                            valid_selected_words_counted.put(sw, counter+1);
                        } else {
                            valid_selected_words_counted.put(sw, 1);
                        }
                    }    

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }   
        return valid_selected_words_counted;
    }

    
    @PutMapping("{id}/validate")
    public ResponseEntity<String> validateResponses(@PathVariable("id") Long id){

        Optional<Task> opt = taskService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }

        long task_id = opt.get().getId();
        List<_Response> valid_responses = getValidResponses(task_id);
        HashMap<_SelectedWord, Integer> valid_selected_words_counted = getValidSelectedWordsCounted(valid_responses);

        List<_Response> unvalid_responses = getUnvalidResponses(task_id);

        // System.out.println(valid_selected_words_counted);
        // System.out.println(unvalid_responses);


        for (_Response res: unvalid_responses) {
            String res_id = String.valueOf(res.getId());
            URI uri;
            try {
                uri = new URI("http://localhost:8089/api/response/" + res_id + "/selected_word");
                RestTemplate restTemplate = new RestTemplate();
                ResponseEntity<String> responseEntity = restTemplate.getForEntity(uri, String.class); 


                List<_SelectedWord> current_res_sw = new ArrayList<>();
                ObjectMapper mapper = new ObjectMapper();
                long index_start = -1;
                long index_end = -1;
                
                int points = 0;     // each correct selected word gives a point

                try {
                    JsonNode root = mapper.readTree(responseEntity.getBody());
                    JsonNode content_node = root.path("selected_words");

                    for (JsonNode jsonNode : content_node) {

                        index_start = jsonNode.get("index_start").asLong();        
                        index_end = jsonNode.get("index_end").asLong();

                        _SelectedWord sw = new _SelectedWord(index_start, index_end);
                        current_res_sw.add(sw);
                        Integer counter = valid_selected_words_counted.get(sw);
                        if (counter != null) {  
                            if (counter.floatValue() / valid_responses.size() >= 0.5) points++;
                        } 

                    }   
                    // if response is concidered to be valid
                    if ((float)(points)/ content_node.size() >= 0.5) {
                        System.out.println("RESPONSE " + String.valueOf(res_id ) + " IS VALID!");

                        //TODO: PUT request response.validity.true

                        org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);
                        String url = "http://localhost:8087/api/tasks/{task_id}/response/{responce_id}";
                        Map<String, String> map = new HashMap<>();
                        map.put("task_id", String.valueOf(task_id));
                        map.put("responce_id", String.valueOf(res_id));


                        _Response updated_response = new _Response(res.getId(), true);

                        HttpEntity<_Response> requestEntity = new HttpEntity<>(updated_response, headers);
                        restTemplate.put(url, requestEntity, map);


                        for (_SelectedWord sw : current_res_sw){
                            //TODO: POST request NamedEntity(sw)
                            System.out.println(sw);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }       

        return ResponseEntity.status(HttpStatus.OK).body("TEST");
        
    }









}
