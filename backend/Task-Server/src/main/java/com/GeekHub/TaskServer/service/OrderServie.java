package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dto.OrderDto;

import java.util.List;

public interface OrderServie {
    List<OrderDto> getOrderAll() throws Exception;
    OrderDto getOrder(Long orderIdx) throws  Exception;
    void createOrder(OrderDto orderDto) throws Exception;
    void updateOrder(OrderDto orderDto) throws Exception;
    void deleteOrder(Long orderIdx) throws Exception;
    void complitionOrder(Long orderIdx) throws Exception;

}
