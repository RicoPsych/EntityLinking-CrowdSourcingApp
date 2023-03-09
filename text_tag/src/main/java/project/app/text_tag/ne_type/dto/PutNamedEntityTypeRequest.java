package project.app.text_tag.ne_type.dto;

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
import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutNamedEntityTypeRequest {
    private long[] textTags; 

    public static BiFunction<NamedEntityType,PutNamedEntityTypeRequest,NamedEntityType> dtoToEntityUpdater(
        Function<long[],List<TextTag>> tagGetter
    ){
        return (type, request) -> {
            type.setTextTags(tagGetter.apply(request.getTextTags()));
            return type;
        };
    }    
}
