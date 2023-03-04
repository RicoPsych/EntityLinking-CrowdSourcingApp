package project.app.ne_type.ne_type.dto;
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
import project.app.ne_type.ne_type.NamedEntityType;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetNamedEntityTypesResponse {
    
    @ToString
    @Setter
    @EqualsAndHashCode
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    private static class _NamedEntityType{
        String name;
        Long id;
    }

    @Singular
    private List<_NamedEntityType> types;

    public static Function<Collection<NamedEntityType>,GetNamedEntityTypesResponse> entityToDtoMapper(){
        return types ->{
            GetNamedEntityTypesResponseBuilder response = GetNamedEntityTypesResponse.builder();
            types.stream()
                .map(type -> _NamedEntityType.builder()
                    .name(type.getName())
                    .id(type.getId())
                    .build())
                .forEach(response::type);
            return response.build();
        };
    }
}
