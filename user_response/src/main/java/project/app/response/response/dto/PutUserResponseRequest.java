package project.app.response.response.dto;

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
import project.app.response.response.Response;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PutUserResponseRequest {

    private String date;
    private boolean validity;

    public static BiFunction<Response,PutUserResponseRequest,Response> dtoToEntityUpdater(){



        return (response, request) -> {
            if(request.getDate()!= null){
                response.setDate(LocalDate.parse(request.getDate()));
            }
            response.setValidity(request.isValidity());
            return response;
        };
    }
    
}
