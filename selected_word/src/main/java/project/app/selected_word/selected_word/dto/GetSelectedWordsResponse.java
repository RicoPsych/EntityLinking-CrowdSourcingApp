package project.app.selected_word.selected_word.dto;

import java.time.LocalDate;
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
import project.app.selected_word.selected_word.SelectedWord;;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GetSelectedWordsResponse {

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _SelectedWord{
        private long id;
        private long index_start;
        private long index_end;
    }

    @Singular
    private List<_SelectedWord> selected_words;

    public static Function<Collection<SelectedWord>,GetSelectedWordsResponse> entityToDtoMapper(){
        return selected_words ->{
            GetSelectedWordsResponseBuilder response = GetSelectedWordsResponse.builder();
            selected_words.stream()
                .map(selected_word -> _SelectedWord.builder()
                    .id(selected_word.getId())
                    .index_start(selected_word.getIndexStart())
                    .index_end(selected_word.getIndexEnd())
                    .build())
                .forEach(response::selected_word);
            return response.build();
        };
    }


    
}
