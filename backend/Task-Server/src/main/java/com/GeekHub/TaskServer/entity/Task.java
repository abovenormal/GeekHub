package com.GeekHub.TaskServer.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    @Column(name="task_idx")
    private long taskId;

//    @ManyToOne
//    @JoinColumn(name ="username")
//    private User user;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "assign_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime assignTime;

    @Column(name = "fin_time")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime finTime;

    @Column(name = "task_content")
    private String taskContent;

}
