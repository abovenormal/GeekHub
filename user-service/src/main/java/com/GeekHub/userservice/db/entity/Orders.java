package com.GeekHub.userservice.db.entity;

import com.GeekHub.userservice.common.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@DynamicInsert
@DynamicUpdate
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Orders {

    @Id
    @Column(name = "orderIdx")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderIdx;

    @Column(name = "spotIdx")
    private int spotIdx;

    @Column(name = "assignTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime assignTime;

    @Column(name = "fintTime")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime fintTime;

    @Column(name = "orderStatus")
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "orderCount")
    private int orderCount;

    @Column(name = "orderUrl")
    private String orderUrl;
}
