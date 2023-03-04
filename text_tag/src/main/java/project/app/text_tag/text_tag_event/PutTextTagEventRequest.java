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
    
    public static Function<TextTag,PutTextTagEventRequest> entityToDtoMapper(){
        return (request) -> {
            return PutTextTagEventRequest.builder()
            .texts(request.getTexts().stream().mapToLong(text-> text.getId()).toArray()).build();
            //TODO: types and texts
        };
    }
}
