package project.app.text.text.dto;

import java.util.List;
import java.util.function.BiFunction;
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


@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTextRequest {
    private String name;
    private String content;
    private Long[] taskSets;
    private Long[] textTags;

    public static BiFunction<Text,PutTextRequest,Text> dtoToEntityUpdater(
        Function<Long[],List<TextTag>> tagGetter,
        Function<Long[],List<TaskSet>> taskSetGetter
    ){
        return (text, request) -> {
            text.setName(request.getName());
            text.setContent(request.getContent());
            text.setTextTags(tagGetter.apply(request.getTextTags()));
            text.setTaskSets(taskSetGetter.apply(request.getTaskSets()));            
            return text;
        };
    }
}
