package project.app.task.task.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task.task.Task;
import project.app.task.task_set.TaskSet;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PostTasksRequest {
    
    private PostTaskRequest[] tasksRq;

    
    public static Function<PostTasksRequest,List<Task>> dtoToEntityMapper(
        Supplier<TaskSet> taskSetGetter
        ){
        return request -> {
            List<Task> tasks = new ArrayList<>();
            for (PostTaskRequest rq :request.tasksRq){
                tasks.add(PostTaskRequest.dtoToEntityMapper(taskSetGetter).apply(rq));
            }
            return tasks;
        };
    }
}
