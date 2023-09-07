package project.app.text.text;

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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.text.task_set.TaskSet;
import project.app.text.text_tag.TextTag;



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
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="text_id")
    private long id;

    @Column(name="text_name")
    private String name;

    @Column(name="content",columnDefinition = "LONGTEXT")
    private String content;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "texts_text_tags",
        joinColumns = @JoinColumn(name= "text_id"),
        inverseJoinColumns = @JoinColumn(name= "text_tag_id"))
    private List<TextTag> textTags;
 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "texts_task_sets",
        joinColumns = @JoinColumn(name= "text_id"),
        inverseJoinColumns = @JoinColumn(name= "task_set_id"))
    private List<TaskSet> taskSets;
}
