package project.app.raport.response.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.raport.response.Response;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetResponseResponse {
    private Long id;
    private Long[] selectedWords;

    public static Function<Response,GetResponseResponse> entityToDtoMapper(){
        return response -> GetResponseResponse.builder()
            .selectedWords(response.getRaports().stream().map(selectedWord -> response.getId()).toArray(Long[]::new))
            .build();
    }
}
