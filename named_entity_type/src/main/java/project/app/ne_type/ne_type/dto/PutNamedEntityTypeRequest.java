package project.app.ne_type.ne_type.dto;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.ne_type.ne_type.NamedEntityType;
import project.app.ne_type.task_set.TaskSet;
import project.app.ne_type.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutNamedEntityTypeRequest {
    
    private String name;
    private String description;
    private Long[] namedEntityTypeChildren;
    private Long namedEntityTypeParent;        
    private Long[] taskSets;    
    private Long[] textTags;

    public static BiFunction<NamedEntityType,PutNamedEntityTypeRequest,NamedEntityType> dtoToEntityUpdater
    (Function<Long[],List<TextTag>> tagGetter,
    Function<Long,NamedEntityType> typeGetter,Function<Long[],List<TaskSet>> setGetter){
        return (type, request) -> {
            
            type.setName(request.getName());
            type.setDescription(request.getDescription());
            type.setNamedEntityTypeParent(typeGetter.apply(request.getNamedEntityTypeParent()));
            //type.setNamedEntityTypeChildren(typeGetter.apply(request.getNamedEntityTypeChildren()));
            type.setTaskSets(setGetter.apply(request.getTaskSets()));
            type.setTextTags(tagGetter.apply(request.getTextTags()));
            return type;
        };
    }    
}
