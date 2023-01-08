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
public class PostTextEventRequest {
    private long id;

    public static Function<Text,PostTextEventRequest> entityToDtoMapper(){
        return entity -> PostTextEventRequest.builder()
            .id(entity.getId())
            .build();
    }
}
