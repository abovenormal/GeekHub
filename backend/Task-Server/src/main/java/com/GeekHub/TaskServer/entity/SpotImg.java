package com.GeekHub.TaskServer.entity;

import lombok.*;

import javax.persistence.*;

@Entity @Getter @Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class SpotImg {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long spotImgIdx;

    @Column
    String spotName;

    @Column
    String imgUrl;
}
