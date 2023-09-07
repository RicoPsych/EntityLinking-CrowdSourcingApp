package project.app.task_set.task_set;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
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

import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.ne_type.NamedEntityTypeService;
import project.app.task_set.task.PostTasksRequest;
import project.app.task_set.task_set.dto.*;
import project.app.task_set.text.Text;
import project.app.task_set.text.TextService;


@RestController
@RequestMapping("api/task_sets")
public class TaskSetController {
    private TaskSetService taskSetService;
    private NamedEntityTypeService namedEntityTypeService;
    private TextService textService;

    /**
     * Autowired Constructor for TaskSetController
     * @param taskSetService 
     * @param namedEntityTypeService
     * @param textService
     */
    @Autowired
    public TaskSetController(TaskSetService taskSetService, NamedEntityTypeService namedEntityTypeService, TextService textService ){
        this.taskSetService = taskSetService;
        this.namedEntityTypeService = namedEntityTypeService;
        this.textService = textService;
    }

    /**
     * TODO: names?
     * Lists all TaskSets Ids
     * @return response with list of TaskSets
     */
    @GetMapping
    public ResponseEntity<GetTaskSetsResponse> getTaskSets(){
        return ResponseEntity.ok(GetTaskSetsResponse.entityToDtoMapper().apply(taskSetService.findAll()));
    }

    /**
     * Gets Task Set specified with id
     * @param id id of Task Set
     * @return response with Task Set parameters (Code 200) or Error 404 
     */
    @GetMapping("{id}")
    public ResponseEntity<GetTaskSetResponse> getTaskSet(@PathVariable("id") Long id){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }
        return ResponseEntity.ok(GetTaskSetResponse.entityToDtoMapper().apply(opt.get()));

    }

    /**
     * Creates new Task Set with given request
     * @param rq body of the request, includes Texts and Types(Classes)
     * @param builder builder for uri?
     * @return void
     */
    @PostMapping
    public ResponseEntity<Void> postTaskSet(@RequestBody PostTaskSetRequest rq, UriComponentsBuilder builder){
        TaskSet task = PostTaskSetRequest.dtoToEntityMapper( 
            text_ids -> {
            //if text_ids == null -> texts = null;
            List<Text> texts = new ArrayList<>();
            for(Long _id : text_ids){
                    Optional<Text> _opt = textService.find(_id);
                    if(_opt.isPresent()){
                        texts.add(_opt.get());
                    }
                    /**If it doesnt find the tag just skips it TODO:postTaskSet */
            }
            return texts;
            },
            type_ids -> {
                //if type_ids == null -> types = null;
                List<NamedEntityType> types = new ArrayList<>();
                for(Long _id : type_ids){
                        Optional<NamedEntityType> _opt = namedEntityTypeService.find(_id);
                        if(_opt.isPresent()){
                            types.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO:postTaskSet */
                }
                return types;
            }
        ).apply(rq);
        task = taskSetService.add(task);

        return ResponseEntity
            .created(builder
                .pathSegment("api","task_sets","{id}")
                .buildAndExpand(task.getId()).toUri())
            .build();  
    }


    @PostMapping("{id}/create")
    public ResponseEntity<Void> createNewTasks(@PathVariable("id") Long id, @Value("${services.tasks}") String task_url,@Value("${services.texts}") String text_url){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "TaskSet not found").build();
        }

        RestTemplate taskController = new RestTemplateBuilder().rootUri(task_url).build();
        RestTemplate textController = new RestTemplateBuilder().rootUri(text_url).build();


        
        for (Text text : opt.get().getTexts()) {
            ResponseEntity<String> response = textController.getForEntity("/api/texts/{text_id}", String.class, text.getId());
            ObjectMapper mapper = new ObjectMapper();

            String content = "";
            long textId = -1;
            try {
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode content_node = root.path("content");
                JsonNode textId_node = root.path("id");
                content = content_node.asText();
                textId = textId_node.asLong();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (content == ""){
                return ResponseEntity.noContent().build();
            }
            
            
            List<Long> indexes = new ArrayList<Long>();
            indexes.add(0l);
            int size = 0;
            String[] split =  content.split("\\.");

            for (int i = 0; i< split.length;i++){
                size += split[i].length();
                if(size > 50 || i == split.length-1){
                    indexes.add(indexes.get(indexes.size()-1)+size);
                    size = 0;
                }
            }

            PostTasksRequest rq = PostTasksRequest.CreateRequest(id,textId, indexes.stream().mapToLong(index->index.longValue()).toArray(), LocalDate.now().toString()).get();
            taskController.postForLocation("/api/task_sets/{task_set_id}/tasks/bulk", rq, id);
        }
        


        return ResponseEntity.created(null).build();
    }
    /**
     * Deletes Task Set with given id, this also deletes all tasks connected with this Task Set
     * @param id    id of task set to be deleted
     * @return  void
     * 
     */
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteTaskSet(@PathVariable("id") Long id){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }            
        taskSetService.delete(opt.get());
        return ResponseEntity.accepted().build();
    }

    /**
     * Updates Task Set with new parameters from recived request
     * @param id    id of task set to be updated
     * @param rq    body of the request, includes arrays of ids of texts and types(classes) 
     * @return      void
     */
    @PutMapping("{id}")
    public ResponseEntity<Void> updateTaskSet(@PathVariable("id") Long id,@RequestBody PutTaskSetRequest rq){
        Optional<TaskSet> opt = taskSetService.find(id);
        if (opt.isEmpty()){
            return ResponseEntity.notFound().header("Description", "Task not found").build();
        }


        taskSetService.update(PutTaskSetRequest.dtoToEntityUpdater( 
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
            },
            type_ids -> {
                List<NamedEntityType> types = new ArrayList<>();
                for(Long _id : type_ids){
                        Optional<NamedEntityType> _opt = namedEntityTypeService.find(_id);
                        if(_opt.isPresent()){
                            types.add(_opt.get());
                        }
                        /**If it doesnt find the tag just skips it TODO: updateTaskSet*/
                }
                return types;
            })
            .apply(opt.get(),rq),true);

        return ResponseEntity.accepted().build();
    }
}
