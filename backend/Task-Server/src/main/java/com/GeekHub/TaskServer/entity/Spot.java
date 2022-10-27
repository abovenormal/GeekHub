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
    private long spotIdx;

    @Enumerated(EnumType.ORDINAL)
    @Column(name="spotcategory")
    private SpotCategory spotCategory;

    @Column(name="spotname")
    private String spotName;

    @Column(name="lat")
    private double lat;

    @Column(name="lon")
    private double lon;

}
