package com.GeekHub.TaskServer.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @GeneratedValue
    @Column(name="task_idx")
    private long spotId;
}
