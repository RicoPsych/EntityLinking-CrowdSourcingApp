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

    public static BiFunction<Response,PutUserResponseRequest,Response> dtoToEntityUpdater(){
        return (responce, request) -> {
            responce.setDate(LocalDate.parse(request.getDate()));        
            return responce;
        };
    }
    
}
