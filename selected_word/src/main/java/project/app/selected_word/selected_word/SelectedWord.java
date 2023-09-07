package project.app.selected_word.selected_word;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import project.app.selected_word.response.Response;
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
@Table(name="selected_word")
public class SelectedWord implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name="selected_Word_id")
    private long id;

    @Column(name="index_start")
    private long indexStart;

    @Column(name="index_end")
    private long indexEnd;

    @ManyToOne
    @JoinColumn(name="user_responce_id")
    private Response response;


}