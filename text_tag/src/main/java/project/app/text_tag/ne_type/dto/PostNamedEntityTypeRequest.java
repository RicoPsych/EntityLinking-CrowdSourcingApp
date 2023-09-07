package project.app.text_tag.ne_type.dto;
import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostNamedEntityTypeRequest {
    //private long id;

    private long[] textTags;

    public static Function<PostNamedEntityTypeRequest, NamedEntityType> dtoToEntityMapper(
        Function<long[],List<TextTag>> tagGetter
    ){
        return request -> NamedEntityType.builder()
            .textTags(tagGetter.apply(request.getTextTags()))
            .build();

    }
}
