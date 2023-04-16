package project.app.response.response.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.response.response.Response;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class GetUserResponseResponse {
    private Long id;
    private String date;

    public static Function<Response,GetUserResponseResponse> entityToDtoMapper(){
        return response -> GetUserResponseResponse.builder()
            .id(response.getId())
            .date(response.getDate().toString())
            .build();
    }
}
