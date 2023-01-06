package project.app.task_set.task_set;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.task_set.text.Text;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TaskSet implements Serializable {
    @Id
    @Column(name="task_set_id")
    private long id;

    @ManyToMany
    @JoinTable(name = "text", 
    joinColumns = @JoinColumn(name="task_set_id"),
    inverseJoinColumns = @JoinColumn(name = "text_id"))
    private List<Text> texts;


    //TODO: private List<NamedEntityType>
}
