package project.app.ne_type.text_tag.dto;
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
import project.app.ne_type.ne_type.NamedEntityType;
import project.app.ne_type.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutTextTagRequest {
    private long[] namedEntityTypes;

    public static BiFunction<TextTag,PutTextTagRequest,TextTag> dtoToEntityUpdater(Function<long[],List<NamedEntityType>> typeGetter){
        return (textTag, request) -> {
            textTag.setNamedEntityTypes(typeGetter.apply(request.getNamedEntityTypes()));
            return textTag;
        };
    }
}
