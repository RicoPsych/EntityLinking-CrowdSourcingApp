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
import project.app.task_set.task_set.TaskSet;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetTaskSetResponse {
    private Long id;
    private Long[] texts;
    private Long[] namedEntityTypes;

    /**
     * <p> GET </p>
     * Static function to map TaskSet entity to DTO
     * @return response with entity
     */
    public static Function<TaskSet,GetTaskSetResponse> entityToDtoMapper(){
        return taskSet -> GetTaskSetResponse.builder()
            .id(taskSet.getId())
            .texts(taskSet.getTexts().stream().map(text -> text.getId()).toArray(Long[]::new))
            .namedEntityTypes(taskSet.getNamedEntityTypes().stream().map(type -> type.getId()).toArray(Long[]::new))
            .build();
    }
}
