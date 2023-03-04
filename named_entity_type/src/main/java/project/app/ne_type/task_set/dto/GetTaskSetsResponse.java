package project.app.ne_type.task_set.dto;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import project.app.ne_type.task_set.TaskSet;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTaskSetsResponse {
    
    @Singular
    private List<Long> taskSets;

    /**
     * <p> GET </p>
     * 
     * Static function to map all TaskSets entities to DTO
     * @return response with list of TaskSets' ids 
     */
    public static Function<Collection<TaskSet>,GetTaskSetsResponse> entityToDtoMapper(){
        return taskSets ->{
            GetTaskSetsResponseBuilder response = GetTaskSetsResponse.builder();
            taskSets.stream()
                .map(taskSet -> taskSet.getId())
                .forEach(response::taskSet); ///TODO nw czy to dobrze XDDDD
            return response.build();
        };
    }
}
