package project.app.task_set.ne_type.dto;
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


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostNamedEntityTypeRequest {
    private Long id;
    private Long[] taskSets;
    /**
     * Static function to map dto to NamedEntityType representation entity
     * @return NamedEntityType
     */
    public static Function<PostNamedEntityTypeRequest, NamedEntityType> dtoToEntityMapper(Function<Long[],List<TaskSet>> taskSetGetter){
        return request -> NamedEntityType.builder()
            .taskSets(taskSetGetter.apply(request.getTaskSets()))
            .build();

    }
}
