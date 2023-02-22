package project.app.text.text.dto;

import java.util.Collection;
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
import project.app.text.text.Text;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetTextsResponse {

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _Text{
        String name;
        Long id;
        //TODO: TAGI
    }

    @Singular
    private List<_Text> texts;

    public static Function<Collection<Text>,GetTextsResponse> entityToDtoMapper(){
        return texts ->{
            GetTextsResponseBuilder response = GetTextsResponse.builder();
            texts.stream()
                .map(text -> _Text.builder()
                    .name(text.getName())
                    .id(text.getId())
                    .build())
                .forEach(response::text); ///TODO TAGI i task sety
            return response.build();
        };
    }

}
