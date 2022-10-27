package com.GeekHub.TaskServer.dto;

import com.GeekHub.TaskServer.entity.*;
import com.GeekHub.TaskServer.util.ModelMapperUtils;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
    private long orderIdx;
    private Orderstatus orderStatus;
    private LocalDateTime assignTime;
    private LocalDateTime finTime;
    private int count;
    private String orderUrl;
    private long spotIdx;

    public static OrderDto of(Order orderEntity){
        OrderDto orderDto = ModelMapperUtils.getModelMapper().map(orderEntity, OrderDto.class);
        return orderDto;
    }
}
