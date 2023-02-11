package project.app.text_tag.text_tag.dto;
import java.util.List;
import java.util.function.BiFunction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PutTextTagRequest {
    private String name;
    private String description;
    private long[] types;

    public static BiFunction<TextTag,PutTextTagRequest,TextTag> dtoToEntityUpdater(){
        return (textTag, request) -> {
            textTag.setName(request.getName());
            textTag.setDescription(request.getDescription());
            //TODO: types and texts
            return textTag;
        };
    }
}
