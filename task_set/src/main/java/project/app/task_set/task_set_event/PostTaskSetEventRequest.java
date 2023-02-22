package project.app.task_set.task_set_event;

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
public class PostTaskSetEventRequest {
    private long id;

    public static Function<TaskSet,PostTaskSetEventRequest> entityToDtoMapper(){
        return entity -> PostTaskSetEventRequest.builder()
            .id(entity.getId())
            .build();
    }
}

