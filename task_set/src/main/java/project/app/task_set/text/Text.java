package project.app.task_set.text;
import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
import project.app.task_set.task_set.TaskSet;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="texts")
public class Text implements Serializable{
    @Id
    @Column(name="text_id")
    private long id;

    @ManyToMany (mappedBy = "texts")
    @ToString.Exclude
    private List<TaskSet> taskSets;
}
