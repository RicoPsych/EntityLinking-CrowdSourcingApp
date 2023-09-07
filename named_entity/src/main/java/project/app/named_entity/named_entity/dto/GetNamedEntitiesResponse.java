package project.app.named_entity.named_entity.dto;
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
import project.app.named_entity.named_entity.NamedEntity;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNamedEntitiesResponse {
    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    static private class _NamedEntity{
        private long id; 
        private Long indexStart;    
    }

    @Singular
    private List<_NamedEntity> entities;


    public static Function<List<NamedEntity>,GetNamedEntitiesResponse> entityToDtoMapper(){
        return entities ->{
            GetNamedEntitiesResponseBuilder response = GetNamedEntitiesResponse.builder();
            entities.stream()
                .map(entity -> _NamedEntity.builder()
                    .id(entity.getId())
                    .indexStart(entity.getIndexStart())
                    .build())
                .forEach(response::entity);

            return response.build();
        };
    }
}
