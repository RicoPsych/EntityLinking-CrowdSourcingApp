package project.app.text_tag.text_tag_event;

import java.util.List;
import java.util.function.Function;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PutTextTagEventRequest {
    private long[] texts;
    private long[] namedEntityTypes;

    public static Function<TextTag,PutTextTagEventRequest> entityToDtoMapper(){
        return (entity) -> {
            return PutTextTagEventRequest.builder()
            .texts(entity.getTexts().stream().mapToLong(text->text.getId()).toArray())
            .namedEntityTypes(entity.getNamedEntityTypes().stream().mapToLong(type->type.getId()).toArray())
            .build();
            
        };
    }
}
