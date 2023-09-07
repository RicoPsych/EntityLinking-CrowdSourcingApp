package project.app.text.task_set.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.task_set.TaskSet;
import project.app.text.text.Text;

@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTaskSetRequest {
    private long[] texts; 

    /**
     * <p> POST </p>
     * Static function to map DTO to TaskSet entity
     * @param textGetter function for getting Text entities list from ids list
     * @param typeGetter function for getting NamedEntitiesType entities list from ids list
     * @return TaskSet entity 
    */
    public static Function<PostTaskSetRequest,TaskSet> dtoToEntityMapper(
        Function<long[],List<Text>> textGetter
    ){
        return request -> TaskSet.builder()
            .texts(textGetter.apply(request.getTexts()))
            .build();
        
    };
}
