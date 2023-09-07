package project.app.raport.raport;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.raport.response.Response;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="raport")
public class Raport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name="raport_id")
    private long id;

    @Column(name="content")
    private String content;

    @ManyToOne
    @JoinColumn(name="user_responce_id")
    private Response response;

    
}
