package project.app.ne_type.task_set.dto;

import java.util.List;
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

@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTaskSetRequest {
    private Long[] namedEntityTypes;    

    /**
     * <p> POST </p>
     * Static function to map DTO to TaskSet entity
     * @param typeGetter function for getting NamedEntitiesType entities list from ids list
     * @return TaskSet entity 
    */
    public static Function<PostTaskSetRequest,TaskSet> dtoToEntityMapper(Function<Long[],List<NamedEntityType>> typeGetter){
        
        return request -> TaskSet.builder()
            .namedEntityTypes(typeGetter.apply(request.getNamedEntityTypes()))
            .build();
        
    };
}
