package project.app.text_tag.text.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.text.Text;
import project.app.text_tag.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostTextRequest {
    //private long id;
    private long[] textTags;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(
        Function<long[],List<TextTag>> tagGetter
    ){
        return request -> Text.builder()
            .textTags(tagGetter.apply(request.getTextTags()))
            .build();
    }
}
