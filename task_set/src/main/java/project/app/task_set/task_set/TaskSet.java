package project.app.task_set.task_set;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.ne_type.NamedEntityType;
import project.app.task_set.text.Text;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="task_sets")
public class TaskSet implements Serializable {

    @Id
    @Column(name="task_set_id")
    private long id;

    @ManyToMany
    @JoinTable(name = "text", 
        joinColumns = @JoinColumn(name="task_set_id"),
        inverseJoinColumns = @JoinColumn(name = "text_id"))
    private List<Text> texts;

    @ManyToMany
    @JoinTable(name = "named_entity_types", 
        joinColumns = @JoinColumn(name="task_set_id"),
        inverseJoinColumns = @JoinColumn(name = "named_entity_type_id"))
    private List<NamedEntityType> namedEntityTypes;

}
