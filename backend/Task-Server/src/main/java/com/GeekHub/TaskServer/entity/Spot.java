package com.GeekHub.TaskServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "spot")
public class Spot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="spotIdx")
    private long spotIdx;

    @Enumerated(EnumType.STRING)
    @Column(name="spotcategory")
    private SpotCategory spotCategory;

    @Column(name="spotname")
    private String spotName;

    @Column(name="lat")
    private double lat;

    @Column(name="lon")
    private double lon;

    @Column(name = "expected_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime expected_time;

    @Column(name="count")
    private int count;

    @Column(name="status")
    private int status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private User user;

}
