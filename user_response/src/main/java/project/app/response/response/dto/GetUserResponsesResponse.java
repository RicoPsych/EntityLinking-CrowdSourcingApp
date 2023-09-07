package project.app.response.response.dto;

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
import project.app.response.response.Response;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GetUserResponsesResponse {

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _Response{
        private Long id;
        private String date;
        private boolean validity;
    }

    @Singular
    private List<_Response> user_responses;

    public static Function<Collection<Response>,GetUserResponsesResponse> entityToDtoMapper(){
        return user_responses ->{
            GetUserResponsesResponseBuilder response = GetUserResponsesResponse.builder();
            user_responses.stream()
                .map(user_response -> _Response.builder()
                    .id(user_response.getId())
                    .date(user_response.getDate().toString())
                    .validity(user_response.isValidity())
                    .build())
                .forEach(response::user_response);
            return response.build();
        };
    }


    
}
