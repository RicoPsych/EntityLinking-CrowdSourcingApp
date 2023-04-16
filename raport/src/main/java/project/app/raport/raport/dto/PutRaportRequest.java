package project.app.raport.raport.dto;

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
import project.app.raport.raport.Raport;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class PutRaportRequest {

    private String content;

    public static BiFunction<Raport,PutRaportRequest,Raport> dtoToEntityUpdater(){
        return (raport, request) -> {
            raport.setContent(request.getContent());        
            return raport;
        };
    }
    
}
