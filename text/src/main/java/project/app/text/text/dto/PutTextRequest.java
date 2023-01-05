package project.app.text.text.dto;

import java.util.function.BiFunction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.text.Text;


@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTextRequest {
    private String name;
    private String content;

    public static BiFunction<Text,PutTextRequest,Text> dtoToEntityUpdater(){
        return (text, request) -> {
            text.setName(request.getName());
            text.setContent(request.getContent());
            return text;
        };
    }
}
