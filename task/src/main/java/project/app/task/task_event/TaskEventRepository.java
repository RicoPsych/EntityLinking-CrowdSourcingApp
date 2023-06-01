package project.app.task.task_event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.task.task.Task;

@Repository
public class TaskEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    TaskEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate =new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(Task task){
        for(RestTemplate template : restTemplates){
            template.delete("/api/task_sets/{task_set}/tasks/{task}",task.getTaskSet().getId() ,task.getId());
        }
    }

    public void save(Task task){
        for(RestTemplate template : restTemplates){
            //template.postForLocation("/api/task_sets/{task_set}/tasks", PostTaskEventRequest.entityToDtoMapper().apply(task) ,task.getTaskSet().getId());
            //template.postForLocation("/api/tasks/{task}", PostTaskEventRequest.entityToDtoMapper().apply(task), task.getId());
            template.postForLocation("/api/tasks", PostTaskEventRequest.entityToDtoMapper().apply(task));
            //template.postForLocation("/api/task_sets", PostTaskSetEventRequest.entityToDtoMapper().apply(set));
        }
    }

    public void update(Task task){
        for(RestTemplate template : restTemplates){
            template.put("/api/task_sets/{task_set}/tasks/{task}", PutTaskEventRequest.entityToDtoMapper().apply(task),task.getTaskSet().getId(),task.getId());
        }
    }
}
