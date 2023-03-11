package project.app.text.text_tag.dto;
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
import project.app.text.text.Text;
import project.app.text.text_tag.TextTag;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PutTextTagRequest {
    private long[] texts;

    public static BiFunction<TextTag, PutTextTagRequest, TextTag> dtoToEntityUpdater(
        Function<long[],List<Text>> textGetter
    ){
        return (textTag, request) -> {
            textTag.setTexts(textGetter.apply(request.getTexts()));
            return textTag;
        };

    }
}
