package project.app.task.task.dto;

import java.time.LocalDate;
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

public class PostTaskRequest {
    private long indexStart;
    private long indexEnd;
    private String startDate;
    private String endDate;
    private long textId;
    private long submitionsNum;
    private long taskSet;

    public static Function<PostTaskRequest,Task> dtoToEntityMapper(
        Supplier<TaskSet> taskSetGetter
        ){
        return request -> {
            //TODO: DATE Pattern  
            return Task.builder()
            .indexStart(request.getIndexStart())
            .indexEnd(request.getIndexEnd())

            .startDate(LocalDate.parse(request.getStartDate()))
            .endDate(LocalDate.parse(request.getEndDate()))
            .textId(request.getTextId())
            .submitionsNum(request.getSubmitionsNum())
            .taskSet(taskSetGetter.get())//.apply(request.getTaskSetId()))
            .build();
        };
    }
}
