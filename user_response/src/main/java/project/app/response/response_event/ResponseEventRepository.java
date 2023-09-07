package project.app.response.response_event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.response.response.Response;;

@Repository
public class ResponseEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    ResponseEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate =new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(Response response){
        for(RestTemplate template : restTemplates){
            template.delete("/api/response/{response}", response.getId());
        }
    }

    public void save(Response response){
        for(RestTemplate template : restTemplates){
            template.postForLocation("/api/response", PostResponseEventRequest.entityToDtoMapper().apply(response));
        }
    }

}
