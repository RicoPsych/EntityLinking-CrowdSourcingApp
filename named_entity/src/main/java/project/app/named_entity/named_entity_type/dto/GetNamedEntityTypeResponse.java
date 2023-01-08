package project.app.named_entity.named_entity_type.dto;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.named_entity.named_entity_type.NamedEntityType;
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNamedEntityTypeResponse {
    private Long id;
    private Long[] namedEntities;

    public static Function<NamedEntityType,GetNamedEntityTypeResponse> entityToDtoMapper(){
        return type -> GetNamedEntityTypeResponse.builder()
            .namedEntities(type.getNamedEntities().stream().map(entity -> entity.getId()).toArray(Long[]::new))
            .build();
    }
}
