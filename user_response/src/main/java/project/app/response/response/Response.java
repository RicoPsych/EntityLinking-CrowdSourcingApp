package project.app.response.response;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name="user_response")

/** User response business class
 * @param id - response id
 * @param task_id - id of task
 * @param text_id - id of text
 * @param date - date of upload
 */
public class Response implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name="id")
    private long id;


    // @Column(name="task")
    // private Task task;

    // @Column(name="text")
    // private Text text;

    @Column(name="upload_date")
    private LocalDate date;

    // private List<T> chosen_words; TODO:

    // private Optional<T> raport; TODO:
}
