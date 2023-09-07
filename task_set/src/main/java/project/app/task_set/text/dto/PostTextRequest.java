package project.app.task_set.text.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.task_set.TaskSet;
import project.app.task_set.text.Text;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTextRequest {
    //private Long id;
    private Long[] taskSets;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(Function<Long[], List<TaskSet>> taskSetGetter){
        return request -> Text.builder()
            .taskSets(taskSetGetter.apply(request.getTaskSets()))
            .build();
    }
}
