package project.app.ne_type.text_tag.dto;

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
import project.app.ne_type.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTextTagRequest {
    private long[] types;

    public static Function<PostTextTagRequest, TextTag> dtoToEntityMapper(Function<long[],List<NamedEntityType>> typeGetter){
        return request -> TextTag.builder()
            .types(typeGetter.apply(request.getTypes()))
        //TODO: types and texts
            .build();

    }
}
