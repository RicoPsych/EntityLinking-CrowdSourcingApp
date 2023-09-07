package project.app.raport.raport.dto;

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

public class GetRaportResponse {
    private Long id;
    private String content;
    private long response_id;

    public static Function<Raport,GetRaportResponse> entityToDtoMapper(){
        return raport -> GetRaportResponse.builder()
            .id(raport.getId())
            .content(raport.getContent().toString())
            .response_id(raport.getResponse().getId())
            .build();
    }
    
}
