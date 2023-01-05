package project.app.text.text.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.text.Text;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTextRequest {
    private String name;
    private String content;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(){
        return request -> Text.builder()
            .name(request.getName())
            .content(request.getContent())
            .build();

    }
}
