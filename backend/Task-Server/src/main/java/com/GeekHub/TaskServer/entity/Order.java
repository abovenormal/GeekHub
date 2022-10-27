package com.GeekHub.TaskServer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_idx")
    private long orderIdx;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status")
    private Orderstatus orderStatus;

    @Column(name = "assign_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime assignTime;

    @Column(name = "fin_time")
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Seoul")
    private LocalDateTime finTime;

    @Column(name="order_count")
    private int count;

    @Column(name="order_url")
    private String orderUrl;

    /* 연관관계 매핑 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spotIdx")
    private Spot spot;

}
