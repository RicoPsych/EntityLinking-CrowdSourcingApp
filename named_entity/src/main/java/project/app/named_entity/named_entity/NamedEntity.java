package project.app.named_entity.named_entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.named_entity.named_entity_type.NamedEntityType;
import project.app.named_entity.text.Text;


@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="named_entity")
public class NamedEntity implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="index_start")
    private Long indexStart;

    @Column(name="index_end")
    private Long indexEnd;

    @Column(name="kb_link",nullable = true)
    private String kb_link;
    
    @ManyToOne
    @JoinColumn(name="named_entity_type_id",nullable = true)
    private NamedEntityType type;

    @ManyToOne
    @JoinColumn(name="text_id")
    private Text text;
}
