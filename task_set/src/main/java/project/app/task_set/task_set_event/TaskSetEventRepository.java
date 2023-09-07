package project.app.task_set.task_set_event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.task_set.task_set.TaskSet;

@Repository
public class TaskSetEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    TaskSetEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate =new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(TaskSet set){
        for(RestTemplate template : restTemplates){
            template.delete("/api/task_sets/{text}", set.getId());
        }
    }

    public void save(TaskSet set){
        for(RestTemplate template : restTemplates){
            template.postForLocation("/api/task_sets", PostTaskSetEventRequest.entityToDtoMapper().apply(set));
        }
    }

    public void update(TaskSet set){
        for(RestTemplate template : restTemplates){
            template.put("/api/task_sets/{tag}",PutTaskSetEventRequest.entityToDtoMapper().apply(set),set.getId());
        }
    }

}
