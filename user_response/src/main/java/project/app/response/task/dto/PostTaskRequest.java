package project.app.response.task.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.response.response.Response;
import project.app.response.task.Task;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTaskRequest {
    private long id;

    public static Function<PostTaskRequest, Task> dtoToEntityMapper(){

        return request -> Task.builder()
            .build();
    }
}