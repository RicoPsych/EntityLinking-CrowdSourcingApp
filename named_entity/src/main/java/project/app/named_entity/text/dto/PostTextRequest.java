package project.app.named_entity.text.dto;

import java.util.List;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.named_entity.named_entity.NamedEntity;
import project.app.named_entity.text.Text;


@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostTextRequest {
    private long id;
    //private long[] namedEntities;

    public static Function<PostTextRequest, Text> dtoToEntityMapper(/*Function<long[],List<NamedEntity>> entityGetter*/){

        return request -> Text.builder()
            //.namedEntities(entityGetter.apply(request.getNamedEntities()))
            .build();
    }
}
