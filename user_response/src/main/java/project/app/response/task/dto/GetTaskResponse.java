package project.app.response.task.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.response.task.Task;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetTaskResponse {

    private Long id;
    private Long[] responses;

    public static Function<Task,GetTaskResponse> entityToDtoMapper(){
        return task -> GetTaskResponse.builder()
            .responses(task.getResponses().stream().map(response -> task.getId()).toArray(Long[]::new))
            .build();
    }
}