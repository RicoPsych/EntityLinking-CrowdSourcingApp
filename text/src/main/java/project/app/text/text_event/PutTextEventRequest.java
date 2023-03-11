package project.app.text.text_event;

import java.util.function.Function;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import project.app.text.text.Text;
@ToString
@Setter
@EqualsAndHashCode
@Getter 
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutTextEventRequest {
    private long[] textTags;
    private long[] taskSets;

    public static Function<Text,PutTextEventRequest> entityToDtoMapper(){
        return (entity) -> {
            return PutTextEventRequest.builder()
            .textTags(entity.getTextTags().stream().mapToLong(tag->tag.getId()).toArray())
            .taskSets(entity.getTaskSets().stream().mapToLong(taskSet->taskSet.getId()).toArray())
            .build();
        };
    }
}
