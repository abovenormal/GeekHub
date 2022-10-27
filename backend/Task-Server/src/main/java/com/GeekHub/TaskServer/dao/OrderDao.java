package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Order;

import java.util.List;

public interface OrderDao {
    List<Order> getOrderAll() throws Exception;
    Order getOrder(Long orderIdx) throws Exception;
    void saveOrder(Order order) throws Exception;
    void deleteOrder(Long orderIdx) throws Exception;
}
