package project.app.response.response_event;

import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.response.response.Response;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostResponseEventRequest {
    private long id;

    public static Function<Response,PostResponseEventRequest> entityToDtoMapper(){
        return entity -> PostResponseEventRequest.builder()
            .id(entity.getId())
            .build();
    }
}
