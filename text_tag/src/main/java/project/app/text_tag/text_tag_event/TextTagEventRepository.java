package project.app.text_tag.text_tag_event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.text_tag.text_tag.TextTag;


@Repository
public class TextTagEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    TextTagEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate =new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(TextTag tag){
        for(RestTemplate template : restTemplates){
            template.delete("/api/tags/{tag}", tag.getId());
        }
    }

    public void update(TextTag tag){
        for(RestTemplate template : restTemplates){
            template.put("/api/tags/{tag}",PutTextTagEventRequest.entityToDtoMapper().apply(tag),tag.getId());
        }
    }

    public void save(TextTag tag){
        for(RestTemplate template : restTemplates){
            template.postForLocation("/api/tags", PostTextTagEventRequest.entityToDtoMapper().apply(tag));
        }
    }
}
