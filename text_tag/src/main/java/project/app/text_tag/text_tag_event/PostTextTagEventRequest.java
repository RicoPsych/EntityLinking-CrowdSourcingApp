package project.app.text_tag.text_tag_event;

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

public class PostTextTagEventRequest {
    private long id;
    private long[] namedEntityTypes;
    private long[] texts;

    public static Function<TextTag,PostTextTagEventRequest> entityToDtoMapper(){
        return entity -> PostTextTagEventRequest.builder()
            .id(entity.getId())
            .texts(entity.getTexts().stream().mapToLong(text->text.getId()).toArray())
            .namedEntityTypes(entity.getNamedEntityTypes().stream().mapToLong(type->type.getId()).toArray())
            .build();
    }
}
