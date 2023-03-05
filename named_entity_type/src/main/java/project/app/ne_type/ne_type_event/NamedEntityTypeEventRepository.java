package project.app.ne_type.ne_type_event;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.ne_type.ne_type.NamedEntityType;



@Repository
public class NamedEntityTypeEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    NamedEntityTypeEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate = new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(NamedEntityType type){
        for(RestTemplate template : restTemplates){
            template.delete("/api/netypes/{type}", type.getId());
        }
    }

    public void update(NamedEntityType type){
        for(RestTemplate template : restTemplates){
            template.put("/api/netypes/{type}",PutNamedEntityTypeEventRequest.entityToDtoMapper().apply(type),type.getId());
        }
    }

    public void save(NamedEntityType type){
        for(RestTemplate template : restTemplates){
            template.postForLocation("/api/netypes", PostNamedEntityTypeEventRequest.entityToDtoMapper().apply(type));
        }
    }
}
