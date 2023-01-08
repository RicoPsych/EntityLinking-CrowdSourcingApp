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
    private Long[] tag_ids;
    private Long[] task_ids;

    public static BiFunction<Text,PutTextRequest,Text> dtoToEntityUpdater(Function<Long[],List<TextTag>> tagGetter){
        return (text, request) -> {
            text.setName(request.getName());
            text.setContent(request.getContent());
            
            text.setTags(tagGetter.apply(request.getTag_ids()));
            //text.tasks(taskGetter.apply(request.getTask_ids()))
            return text;
        };
    }
}
