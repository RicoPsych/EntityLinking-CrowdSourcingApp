package project.app.task.task_set.dto;

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
import project.app.task.task.Task;
import project.app.task.task_set.TaskSet;


@ToString
@Setter
@EqualsAndHashCode
@Getter
//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTaskSetRequest {
    //private long[] tasks;
    /**
     * <p> PUT </p>
     * Static function to map DTO to TaskSet entity for updating purposes
     * @param textGetter function for getting Text entities list from ids list
     * @param typeGetter function for getting NamedEntitiesType entities list from ids list
     * @return TaskSet 
     */
    public static BiFunction<TaskSet,PutTaskSetRequest,TaskSet> dtoToEntityUpdater(
        Function<long[],List<Task>> taskGetter){
        return (taskSet, request) -> {
            //taskSet.setTasks(taskGetter.apply(request.getTasks()));
            return taskSet;
        };
    }
}
