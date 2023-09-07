package project.app.text_tag.text.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.text.Text;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetTextResponse {
    private Long id;

    public static Function<Text,GetTextResponse> entityToDtoMapper(){
        return text -> GetTextResponse.builder()
                        .id(text.getId())
                        .build();
    }
}
