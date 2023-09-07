package project.app.text_tag.text_tag;

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
import project.app.text_tag.ne_type.NamedEntityType;
import project.app.text_tag.text.Text;



@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="text_tags")
public class TextTag implements Serializable {
    @Id
    @Column(name="text_tag_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name="text_tag_name", unique = true)
    private String name;

    @Column(name="text_tag_description")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "named_entity_types_text_tags",
        joinColumns = @JoinColumn(name= "text_tag_id"),
        inverseJoinColumns = @JoinColumn(name= "named_entity_type_id"))
    private List<NamedEntityType> namedEntityTypes;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "texts_text_tags",
        joinColumns = @JoinColumn(name= "text_tag_id"),
        inverseJoinColumns = @JoinColumn(name= "text_id"))
    private List<Text> texts;
}
