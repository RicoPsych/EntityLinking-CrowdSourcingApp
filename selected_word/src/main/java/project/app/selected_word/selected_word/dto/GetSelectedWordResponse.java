package project.app.selected_word.selected_word.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.selected_word.selected_word.SelectedWord;;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetSelectedWordResponse {
    private long id;
    private Long start_index;
    private Long end_index;
    private long response_id;

    public static Function<SelectedWord,GetSelectedWordResponse> entityToDtoMapper(){
        return selected_word -> GetSelectedWordResponse.builder()
            .id(selected_word.getId())
            .start_index(selected_word.getIndexStart())
            .end_index(selected_word.getIndexEnd())
            .response_id(selected_word.getResponse().getId())
            .build();
    }
}
