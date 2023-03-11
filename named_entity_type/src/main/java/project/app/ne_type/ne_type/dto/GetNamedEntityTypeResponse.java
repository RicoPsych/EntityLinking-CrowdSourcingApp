package project.app.ne_type.ne_type.dto;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.ne_type.ne_type.NamedEntityType;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNamedEntityTypeResponse {

    private Long id;
    private String name;
    private String description;
    private Long namedEntityTypeParent;
    private long[] textTags;
    private long[] taskSets;

    public static Function<NamedEntityType,GetNamedEntityTypeResponse> entityToDtoMapper(){
        
        
        return type -> {
            Long parent = null; 
            if(type.getNamedEntityTypeParent() != null){
                parent = type.getNamedEntityTypeParent().getId();
            }

            return GetNamedEntityTypeResponse.builder()
                        .id(type.getId())
                        .name(type.getName())
                        .description(type.getDescription())
                        .namedEntityTypeParent(parent) //nullable
                        .textTags(type.getTextTags().stream().mapToLong(tag->tag.getId()).toArray())
                        .taskSets(type.getTaskSets().stream().mapToLong(set->set.getId()).toArray())
                        .build();
                    };
    }
}
