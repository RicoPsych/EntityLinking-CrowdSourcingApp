package project.app.task.task_event;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task.task.Task;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTaskEventRequest {
    private long id;

    public static Function<Task,PutTaskEventRequest> entityToDtoMapper(){
        return  entity -> PutTaskEventRequest.builder()
        .id(entity.getId())
        .build();
    }
}
