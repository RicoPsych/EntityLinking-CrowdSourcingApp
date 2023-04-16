package project.app.raport.raport.dto;

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
import project.app.raport.raport.Raport;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class GetRaportsResponse {

    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _Raport{
        private Long id;
        private String content;
    }

    @Singular
    private List<_Raport> user_raports;

    public static Function<Collection<Raport>,GetRaportsResponse> entityToDtoMapper(){
        return user_raports ->{
            GetRaportsResponseBuilder response = GetRaportsResponse.builder();
            user_raports.stream()
                .map(user_raport -> _Raport.builder()
                    .id(user_raport.getId())
                    .content(user_raport.getContent())
                    .build())
                .forEach(response::user_raport);
            return response.build();
        };
    }
    
}
