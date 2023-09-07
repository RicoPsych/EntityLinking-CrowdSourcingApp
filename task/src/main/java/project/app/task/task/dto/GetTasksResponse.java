package project.app.task.task.dto;

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
import project.app.task.task.Task;


@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTasksResponse {

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static private class _Task{
        private long id;
    }

    @Singular
    private List<_Task> tasks;

    public static Function<List<Task>,GetTasksResponse> entityToDtoMapper() {
        return tasks ->{

            GetTasksResponseBuilder response = GetTasksResponse.builder();
            tasks.stream()
                .map(task-> _Task.builder()
                    .id(task.getId())
                    .build())
                .forEach(response::task);

            return response.build();
        };
    }
    
}
