package project.app.named_entity.named_entity.dto;
import java.util.function.Function;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.ToString;
import project.app.named_entity.named_entity.NamedEntity;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetNamedEntityResponse {
    private long id;
    private long indexStart;
    private long indexEnd;

    private long text_id;
    private String kb_link;
    private Long type_id;

    public static Function<NamedEntity,GetNamedEntityResponse> entityToDtoMapper(){
        return entity -> {

            //type is nullable
            Long type = null;
            if (entity.getType() != null)
                type = entity.getType().getId();

            return GetNamedEntityResponse.builder()
                .id(entity.getId())
                .indexStart(entity.getIndexStart())
                .indexEnd(entity.getIndexEnd())
                .text_id(entity.getText().getId())
                .kb_link(entity.getKb_link())
                .type_id(type)
                .build();
        };
    }
}
