package project.app.named_entity.named_entity.dto;

import java.util.ArrayList;
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
import project.app.named_entity.named_entity.NamedEntity;
import project.app.named_entity.named_entity_type.NamedEntityType;
import project.app.named_entity.text.Text;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostNamedEntitiesRequest {
    private PostNamedEntityRequest[] namedEntitiesRq; 
    
    public static Function<PostNamedEntitiesRequest, List<NamedEntity>> dtoToEntityMapper(Supplier<Text> textGetter, Function<Long,NamedEntityType> typeGetter)
    {
        return request ->{
            List<NamedEntity> entities = new ArrayList<>();
            for (PostNamedEntityRequest rq : request.namedEntitiesRq){
                entities.add(PostNamedEntityRequest.dtoToEntityMapper(textGetter, typeGetter).apply(rq));
            }
            return entities;
        };
    }

}
