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
import project.app.text.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetTextResponse {
    private Long id;
    private String name;
    private String content;
    private Long[] tags;
    //private Long[] namedEntities;

    public static Function<Text,GetTextResponse> entityToDtoMapper(){
        return text -> GetTextResponse.builder()
            .id(text.getId())
            .name(text.getName())
            .content(text.getContent())
            .tags(text.getTags().stream().map(tag -> tag.getId()).toArray(Long[]::new))
            //.namedEntities(text.getEntities().stream().map(entity -> entity.getId()).toArray(Long[]::new))
            .build();
    }
}
