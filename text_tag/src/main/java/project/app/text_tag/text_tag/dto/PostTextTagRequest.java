package project.app.text_tag.text_tag.dto;

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
import project.app.text_tag.text.Text;
import project.app.text_tag.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTextTagRequest {
    private String name;
    private String description;
    private long[] namedEntityTypes;
    private long[] texts;

    public static Function<PostTextTagRequest, TextTag> dtoToEntityMapper(
        Function<long[],List<NamedEntityType>> typeGetter,
        Function<long[],List<Text>> textGetter
    ){
        return request -> TextTag.builder()
            .name(request.getName())
            .description(request.getDescription())
            .texts(textGetter.apply(request.getTexts()))
            .namedEntityTypes(typeGetter.apply(request.getNamedEntityTypes()))            
            .build();

    }
}
