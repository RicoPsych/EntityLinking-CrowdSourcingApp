package project.app.ne_type.named_entity_type;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NamedEntityType implements Serializable {
    @Id
    @Column(name="named_entity_type_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="named_entity_type_name")
    private String name;

    @Column(name="named_entity_type_description")
    private String description;

    //TODO:
    // @ManyToMany
    // private List<TextTag> textTags;

    // @OneToMany
    // private List<NamedEntity> namedEntities;

    // @ManyToMany 
    // private List<TaskSet> taskSets;
}
