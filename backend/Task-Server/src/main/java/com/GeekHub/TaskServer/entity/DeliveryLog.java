package com.GeekHub.TaskServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity @Getter
@Setter @Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "DELIVERY_LOG")
public class DeliveryLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long logIdx;

    @Column
    private long spotIdx;

    @Enumerated(EnumType.STRING)
    @Column
    private SpotCategory spotCategory;

    @Column
    private String spotName;

    @Column
    private double lat;

    @Column
    private double lon;

    @Column
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime expectedTime;

    @Column
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime arrivedTime;

    @Column
    private String imageUrl;

    @Column
    private int count;

    @Column
    private int status;

    @Column
    private long userIdx;


}
