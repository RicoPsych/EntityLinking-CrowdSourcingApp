package project.app.task.task.dto;

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
public class GetTaskResponse {
    private long id;
    private long indexStart;
    private long indexEnd;
    private String startDate;
    private String endDate;
    private long submitionsNum;
    private long taskSetId;

    static public Function<Task,GetTaskResponse> entityToDtoMapper(){
        return task -> GetTaskResponse.builder()
                .id(task.getId())
                .indexStart(task.getIndexStart())
                .indexEnd(task.getIndexEnd())
            //    .startDate(task.getStartDate().toString())
            //    .endDate(task.getEndDate().toString())
                .submitionsNum(task.getSubmitionsNum())
                .taskSetId(task.getTaskSet().getId())
            .build();
    }
}
