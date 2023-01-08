package project.app.text_tag.text_tag.dto;
import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Singular;
import lombok.ToString;
import project.app.text_tag.text_tag.TextTag;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTextTagsResponse {
    
    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static private class Tags{
        private long id;
        private String name;
    }

    @Singular
    private List<Tags> tags;

    public static Function<List<TextTag>,GetTextTagsResponse> entityToDtoMapper(){
        return tags ->{
            GetTextTagsResponseBuilder response = GetTextTagsResponse.builder();
            tags.stream()
                .map(tag -> Tags.builder()
                    .name(tag.getName())
                    .id(tag.getId())
                    .build())
                .forEach(response::tag); ///TODO TAGI
            return response.build();
        };
    }
}
