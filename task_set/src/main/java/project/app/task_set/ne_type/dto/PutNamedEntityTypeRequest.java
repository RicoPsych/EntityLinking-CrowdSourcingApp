package project.app.task_set.ne_type.dto;

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
import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.task_set.TaskSet;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutNamedEntityTypeRequest {
    private Long[] taskSets;    

    public static BiFunction<NamedEntityType,PutNamedEntityTypeRequest,NamedEntityType> dtoToEntityUpdater(
        Function<Long[],List<TaskSet>> setGetter
    ){
        return (type, request) -> {
            type.setTaskSets(setGetter.apply(request.getTaskSets()));
            return type;
        };
    }    
}
