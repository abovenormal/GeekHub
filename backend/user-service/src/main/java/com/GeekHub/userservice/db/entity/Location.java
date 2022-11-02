package com.GeekHub.userservice.db.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Location")
public class Location {

    @Column(name = "lati")
    private double lati;

    @Column(name = "longi")
    private double longi;

    @Column(name = "locat_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime locat_time;
}