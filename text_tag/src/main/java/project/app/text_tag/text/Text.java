package project.app.text_tag.text;

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
@Table(name="texts")
public class Text implements Serializable{
    @Id
    @Column(name="text_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    //TODO: name??


    @ManyToMany (mappedBy = "texts")
    @ToString.Exclude
    private List<TextTag> textTags; //set? efektywniejsze?
}
