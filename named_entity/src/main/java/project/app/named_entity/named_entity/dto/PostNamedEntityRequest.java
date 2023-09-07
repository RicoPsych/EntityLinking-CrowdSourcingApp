package project.app.named_entity.named_entity.dto;
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

@Builder
@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class PostNamedEntityRequest {
//    private long id;
    private long indexStart;
    private long indexEnd;

    //private long textId;
    private String kbLink;
    private long typeId;

    public static Function<PostNamedEntityRequest, NamedEntity> dtoToEntityMapper(Supplier<Text> textGetter,Function<Long,NamedEntityType> typeGetter){
        return request -> NamedEntity.builder()
            .indexStart(request.getIndexStart())
            .indexEnd(request.getIndexEnd())
            .kb_link(request.getKbLink())
            .text(textGetter.get())
            .type(typeGetter.apply(request.getTypeId()))
            .build();

    }
}
