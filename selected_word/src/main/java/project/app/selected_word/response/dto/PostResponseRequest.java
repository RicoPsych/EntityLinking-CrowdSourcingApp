package project.app.selected_word.response.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.selected_word.selected_word.SelectedWord;
import project.app.selected_word.response.Response;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostResponseRequest {
    private long id;

    public static Function<PostResponseRequest, Response> dtoToEntityMapper(){

        return request -> Response.builder()
            //
            .build();
    }
}
