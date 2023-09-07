package project.app.text.text.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.task_set.TaskSet;
import project.app.text.text.Text;
import project.app.text.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTextRequest {
    private String name;
    private String content;
    private Long[] textTags;
    private Long[] taskSets;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(
        Function<Long[],List<TextTag>> tagGetter,
        Function<Long[],List<TaskSet>> taskSetGetter
    ){

        return request -> Text.builder()
            .name(request.getName())
            .content(request.getContent())
            .textTags(tagGetter.apply(request.getTextTags()))
            .taskSets(taskSetGetter.apply(request.getTaskSets()))
            .build();
    }
}
