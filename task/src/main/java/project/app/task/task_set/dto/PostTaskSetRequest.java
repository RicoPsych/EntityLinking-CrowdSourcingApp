package project.app.task.task_set.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task.task_set.TaskSet;

@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
//@AllArgsConstructor
@NoArgsConstructor

public class PostTaskSetRequest {
    //private long[] tasks; 

    /**
     * <p> POST </p>
     * Static function to map DTO to TaskSet entity
     * @param taskGetter function for getting Task entities list from ids list
     * @return TaskSet entity 
    */
    public static Function<PostTaskSetRequest,TaskSet> dtoToEntityMapper(
        //Function<long[],List<Task>> taskGetter
    ){
        return request -> TaskSet.builder()
            //.tasks(taskGetter.apply(request.getTasks()))
            .build();
        
    };
}
