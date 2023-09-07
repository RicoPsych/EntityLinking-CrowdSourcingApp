package project.app.task_set.task_set.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.task_set.TaskSet;
import project.app.task_set.text.Text;

@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTaskSetRequest {
    private Long[] texts;
    private Long[] namedEntityTypes;    

    /**
     * <p> POST </p>
     * Static function to map DTO to TaskSet entity
     * @param textGetter function for getting Text entities list from ids list
     * @param typeGetter function for getting NamedEntitiesType entities list from ids list
     * @return TaskSet entity 
    */
    public static Function<PostTaskSetRequest,TaskSet> dtoToEntityMapper(
        Function<Long[],List<Text>> textGetter,
        Function<Long[],List<NamedEntityType>> typeGetter
    ){
        return request -> TaskSet.builder()
            .texts(textGetter.apply(request.getTexts()))
            .namedEntityTypes(typeGetter.apply(request.getNamedEntityTypes()))
            .build();
        
    };
}
