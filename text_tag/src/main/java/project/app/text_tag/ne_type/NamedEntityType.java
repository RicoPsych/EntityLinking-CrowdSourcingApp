package project.app.text_tag.ne_type;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text_tag.text_tag.TextTag;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="named_entity_types")
public class NamedEntityType implements Serializable {
    @Id
    @Column(name="named_entity_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO:POTRZEBNE?
    @Column(name="named_entity_type_name")
    private String name;

    @ManyToMany(mappedBy = "namedEntityTypes")
    @ToString.Exclude
    private List<TextTag> textTags;

}
