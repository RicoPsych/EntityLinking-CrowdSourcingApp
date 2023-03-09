package project.app.text_tag.text.dto;

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
import project.app.text_tag.text.Text;
import project.app.text_tag.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTextRequest {
    private long[] textTags;

    public static BiFunction<Text, PutTextRequest, Text> dtoToEntityMapper(
        Function<long[],List<TextTag>> tagGetter
    ){
        return (text , request) -> {
            text.setTextTags(tagGetter.apply(request.getTextTags()));
            return text;
        };
    }
}
