package project.app.response.response.dto;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.response.response.Response;
import project.app.response.task.Task;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostUserResponseRequest {

    private String date;

    public static Function<PostUserResponseRequest, Response> dtoToEntityMapper(Supplier<Task> taskGetter){

        return request -> Response.builder()
            .date(LocalDate.parse(request.getDate()))
            .task(taskGetter.get())
            .build();
    }
   
}
