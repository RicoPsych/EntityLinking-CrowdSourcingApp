package project.app.selected_word.selected_word.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.selected_word.response.Response;
import project.app.selected_word.selected_word.SelectedWord;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostSelectedWordRequest {

    private long start_index;  
    private long end_index;

    public static Function<PostSelectedWordRequest, SelectedWord> dtoToEntityMapper(Supplier<Response> responseGetter){

        return request -> SelectedWord.builder()
            .indexStart(request.getStart_index())
            .indexEnd(request.getEnd_index())
            .response(responseGetter.get())
            .build();
    }
   
}