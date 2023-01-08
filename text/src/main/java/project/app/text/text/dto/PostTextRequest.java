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
    private Long[] tag_ids;
    private Long[] task_ids;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(Function<Long[],List<TextTag>> tagGetter){

        return request -> Text.builder()
            .name(request.getName())
            .content(request.getContent())
            .tags(tagGetter.apply(request.getTag_ids()))
            //.tasks(taskGetter.apply(request.getTask_ids()))
            .build();
    }
}
