package project.app.response.response;

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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


import project.app.response.task.Task;

@ToString
@Setter
@EqualsAndHashCode
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name="user_response")
public class Response implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)    
    @Column(name="user_responce_id")
    private long id;

    @Column(name="upload_date")
    private LocalDate date;

    @ManyToOne
    @JoinColumn(name="task_id")
    private Task task;

}
