package project.app.named_entity.named_entity.dto;
import java.util.function.BiFunction;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.named_entity.named_entity.NamedEntity;
import project.app.named_entity.named_entity_type.NamedEntityType;


@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PutNamedEntityRequest {
    private long indexStart;
    private long indexEnd;
    private String kbLink;
    private long typeId;

    public static BiFunction<NamedEntity,PutNamedEntityRequest, NamedEntity> dtoToEntityUpdater(Function<Long,NamedEntityType> typeGetter){
        return (entity,request) -> {
            entity.setIndexStart(request.getIndexStart());
            entity.setIndexEnd(request.getIndexEnd());
            entity.setKb_link(request.getKbLink());
            entity.setType(typeGetter.apply(request.getTypeId()));
            return entity;
        };
    }
}
