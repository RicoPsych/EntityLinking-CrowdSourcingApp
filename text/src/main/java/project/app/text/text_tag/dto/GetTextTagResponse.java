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

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTextTagResponse {
    private long id;

    public static Function<TextTag,GetTextTagResponse> entityToDtoMapper(){
        return tag -> GetTextTagResponse.builder()
            .id(tag.getId())
            .build();
    }
}
