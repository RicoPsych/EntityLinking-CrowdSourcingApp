package project.app.ne_type.ne_type_event;
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

public class PostNamedEntityTypeEventRequest {
    private long id;
    private long[] textTags;
    private long[] taskSets;

    public static Function<NamedEntityType,PostNamedEntityTypeEventRequest> entityToDtoMapper(){
        return entity -> PostNamedEntityTypeEventRequest.builder()
            .id(entity.getId())
            .textTags(entity.getTextTags().stream().mapToLong(tag -> tag.getId()).toArray())
            .taskSets(entity.getTaskSets().stream().mapToLong(set -> set.getId()).toArray())
            .build();
    }
}
