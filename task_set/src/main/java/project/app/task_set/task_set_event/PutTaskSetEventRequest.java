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

public class PutTaskSetEventRequest {
    private long[] texts;
    private long[] namedEntityTypes;

    public static Function<TaskSet,PutTaskSetEventRequest> entityToDtoMapper(){
        return (entity) -> {
            return PutTaskSetEventRequest.builder()
            .texts(entity.getTexts().stream().mapToLong(text-> text.getId()).toArray())
            .namedEntityTypes(entity.getNamedEntityTypes().stream().mapToLong(type-> type.getId()).toArray())
            .build();
        };
    }
}
