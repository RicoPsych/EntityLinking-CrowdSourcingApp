package project.app.selected_word.selected_word.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.selected_word.selected_word.SelectedWord;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PutSelectedWordRequest {

    private long start_index;
    private long end_index;

    public static BiFunction<SelectedWord,PutSelectedWordRequest,SelectedWord> dtoToEntityUpdater(){
        return (selected_word, request) -> {
            selected_word.setIndexStart(request.getStart_index());
            selected_word.setIndexEnd(request.getEnd_index());  
            return selected_word;
        };
    }
    
}
