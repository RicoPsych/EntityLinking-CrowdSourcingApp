package project.app.text.text_tag.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTextTagRequest {
    private long id;

    public static Function<PostTextTagRequest, TextTag> dtoToEntityMapper(){
        return request -> TextTag.builder()
            //TODO: types and texts
            .build();

    }
}
