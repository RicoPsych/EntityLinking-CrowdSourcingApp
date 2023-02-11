package project.app.text.text_event;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import project.app.text.text.Text;

@Repository
public class TextEventRepository {
    private List<RestTemplate> restTemplates;

    @Autowired
    TextEventRepository(@Value("${services.urls}") String[] urls){
        restTemplates = new ArrayList<RestTemplate>();
        for(String url : urls){ 
            RestTemplate newTemplate =new RestTemplateBuilder().rootUri(url).build();
            restTemplates.add(newTemplate);
        }
    }

    public void delete(Text text){
        for(RestTemplate template : restTemplates){
            template.delete("/api/texts/{text}", text.getId());
        }
    }

    public void save(Text text){
        for(RestTemplate template : restTemplates){
            template.postForLocation("/api/texts", PostTextEventRequest.entityToDtoMapper().apply(text));
        }
    }
}
