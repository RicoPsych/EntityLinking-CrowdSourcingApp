package project.app.raport.raport.dto;

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
import project.app.raport.raport.Raport;
import project.app.raport.response.Response;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostRaportRequest {

    private String content;

    public static Function<PostRaportRequest, Raport> dtoToEntityMapper(Supplier<Response> responseGetter){

        return request -> Raport.builder()
            .content(request.getContent())
            .response(responseGetter.get())
            .build();
    }
    
}
