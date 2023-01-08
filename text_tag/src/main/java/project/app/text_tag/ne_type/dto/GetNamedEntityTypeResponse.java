package project.app.text_tag.ne_type.dto;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.ne_type.NamedEntityType;

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


    public static Function<NamedEntityType,GetNamedEntityTypeResponse> entityToDtoMapper(){
        return namedEntityType -> GetNamedEntityTypeResponse.builder()
                        .id(namedEntityType.getId())
   //                     .name(namedEntityType.getName())
                        .build();
    }
}
