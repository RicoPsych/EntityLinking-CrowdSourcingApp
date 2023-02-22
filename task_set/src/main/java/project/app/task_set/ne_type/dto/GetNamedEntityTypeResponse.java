package project.app.task_set.ne_type.dto;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.ne_type.NamedEntityType;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNamedEntityTypeResponse {

    private long id;
    //private String name;

    /**
     * Static Function to map NamedEntityType representation entity to data transfer object (dto)
     * @return response with entity
     */
    public static Function<NamedEntityType,GetNamedEntityTypeResponse> entityToDtoMapper(){
        return namedEntityType -> GetNamedEntityTypeResponse.builder()
                        .id(namedEntityType.getId())
                        .build();
    }
}
