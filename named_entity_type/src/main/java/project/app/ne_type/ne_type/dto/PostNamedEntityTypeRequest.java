package project.app.ne_type.ne_type.dto;
import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.ne_type.ne_type.NamedEntityType;
import project.app.ne_type.task_set.TaskSet;
import project.app.ne_type.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostNamedEntityTypeRequest {
    private long id;
    private String name;
    private String description;
    private Long namedEntityTypeParent;
//    private Long[] namedEntityTypesChild;//?
    private Long[] textTags;
    private Long[] taskSets;

    public static Function<PostNamedEntityTypeRequest, NamedEntityType> dtoToEntityMapper(
            Function<Long[],List<TextTag>> tagGetter,
            Function<Long,NamedEntityType> typeGetter,
            Function<Long[],List<TaskSet>> setGetter)
    {
        return request -> NamedEntityType.builder()
        .name(request.getName())
        .description(request.getDescription())
        .textTags(tagGetter.apply(request.getTextTags()))
        .taskSets(setGetter.apply(request.getTaskSets()))
        .namedEntityTypeParent(typeGetter.apply(request.getNamedEntityTypeParent()))    
        .build();
    }
}
