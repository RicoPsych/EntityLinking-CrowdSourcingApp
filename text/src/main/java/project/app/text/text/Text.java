package project.app.text.text;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
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

    @Column(name="content")
    private String content;

    // private List<TaskSet> task_set;
    
    // @OneToMany(mappedBy = "text") //, fetch = FetchType.EAGER
    // private List<NamedEntity> entities;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "texts_tags",
        joinColumns = @JoinColumn(name= "text_id"),
        inverseJoinColumns = @JoinColumn(name= "text_tag_id"))
    private List<TextTag> tags;
}
