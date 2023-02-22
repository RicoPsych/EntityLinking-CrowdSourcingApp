package project.app.task_set.text.dto;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.text.Text;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder


//TODO: Tests + Debugging
public class GetTextResponse {
    private Long id;
    private Long[] taskSets;

    public static Function<Text,GetTextResponse> entityToDtoMapper(){
        return text -> GetTextResponse.builder()
            .taskSets(text.getTaskSets().stream().map(entity -> entity.getId()).toArray(Long[]::new))
            .build();
    }
}
