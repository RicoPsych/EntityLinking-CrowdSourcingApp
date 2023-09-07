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
    private long[] textTags;
    private long[] taskSets;

    public static Function<Text,GetTextResponse> entityToDtoMapper(){
        return text -> GetTextResponse.builder()
            .id(text.getId())
            .name(text.getName())
            .content(text.getContent())
            .textTags(text.getTextTags().stream().mapToLong(tag -> tag.getId()).toArray())
            .taskSets(text.getTaskSets().stream().mapToLong(set->set.getId()).toArray())
            .build();
    }
}
