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


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostNamedEntityTypeRequest {
    private Long id;

    public static Function<PostNamedEntityTypeRequest, NamedEntityType> dtoToEntityMapper(){
        return request -> NamedEntityType.builder()
            .build();
    }
}
