package project.app.raport.response.dto;

import java.util.List;
import java.util.function.Function;

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
public class PostResponseRequest {
    private long id;

    public static Function<PostResponseRequest, Response> dtoToEntityMapper(){

        return request -> Response.builder()
            //
            .build();
    }
}
