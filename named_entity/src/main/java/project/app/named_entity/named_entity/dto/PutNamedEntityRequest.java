package project.app.named_entity.named_entity.dto;
import java.util.function.BiFunction;
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
public class PutNamedEntityRequest {
    private long indexStart;
    private long indexEnd;

    private long text_id;
    private String kb_link;
    private long type_id;

    public static BiFunction<NamedEntity,PutNamedEntityRequest, NamedEntity> dtoToEntityUpdater(Supplier<Text> textGetter,Function<Long,NamedEntityType> typeGetter){
        return (entity,request) -> {
            entity.setIndexStart(request.getIndexStart());
            entity.setIndexEnd(request.getIndexEnd());
            entity.setKb_link(request.getKb_link());
            entity.setText(textGetter.get());
            entity.setType(typeGetter.apply(request.getType_id()));
            return entity;
        };
    }
}
