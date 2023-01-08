package project.app.named_entity.text.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.named_entity.text.Text;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTextRequest {
    private Long id;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(){

        return request -> Text.builder()
            .build();
    }
}
