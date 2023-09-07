package project.app.task.task.dto;
import java.util.function.BiFunction;
import java.util.function.Function;

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
public class PutTaskRequest {
    private long indexStart;
    private long indexEnd;
    private String startDate;
    private String endDate;
    private long submitionsNum;
    private long taskSet;

    public static BiFunction<Task ,PutTaskRequest,Task> dtoToEntityMapper(Function<Long,TaskSet> taskSetGetter){
        return (task ,request) -> {
            task.setIndexStart(request.getIndexStart());
            task.setIndexEnd(request.getIndexEnd());
            // TODO: Parse to date task.setStartDate(request.getStartDate())
            
            // TODO: Parse to date task.setEndDate(request.getEndDate())
            task.setSubmitionsNum(request.getSubmitionsNum());
            task.setTaskSet(taskSetGetter.apply(request.getTaskSet()));
            return task;
        };
    }
}
