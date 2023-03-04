package project.app.ne_type.ne_type;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.ne_type.task_set.TaskSet;
import project.app.ne_type.text_tag.TextTag;



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

    @Column(name="named_entity_type_name")
    private String name;

    @Column(name="named_entity_type_description")
    private String description;

    //TODO:

    @OneToMany(mappedBy="namedEntityTypeParent")
    private List<NamedEntityType> namedEntityTypeChildren;

    @ManyToOne
    @JoinColumn(name ="named_entity_type_parent")
    private NamedEntityType namedEntityTypeParent;


    ///
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "named_entity_types_tags",
        joinColumns = @JoinColumn(name= "named_entity_type_id"),
        inverseJoinColumns = @JoinColumn(name= "text_tag_id"))
    private List<TextTag> textTags;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "named_entity_types_task_sets",
        joinColumns = @JoinColumn(name= "named_entity_type_id"),
        inverseJoinColumns = @JoinColumn(name= "task_set_id"))
    private List<TaskSet> taskSets;
}
