package com.GeekHub.TaskServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "task")
public class Task {
    @Id
    @GeneratedValue
    @Column(name="task_idx")
    private long taskIdx;

//    @ManyToOne
//    @JoinColumn(name ="username")
//    private User user;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    @Column(name = "assign_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime assignTime;

    @Column(name = "fin_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime finTime;

    @Column(name = "task_content")
    private String taskContent;

}
