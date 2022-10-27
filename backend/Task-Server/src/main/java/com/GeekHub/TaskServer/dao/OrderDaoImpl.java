package com.GeekHub.TaskServer.dao;

import com.GeekHub.TaskServer.entity.Order;
import com.GeekHub.TaskServer.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderDaoImpl implements OrderDao {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderDaoImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getOrderAll() throws Exception {
        List<Order> orderList = orderRepository.findAll();
        return orderList;
    }

    @Override
    public Order getOrder(Long orderIdx) throws Exception {
        Order orderEntity = orderRepository.getById(orderIdx);
        if(orderEntity == null)
            throw new Exception();
        return orderEntity;
    }

    @Override
    public void saveOrder(Order order) throws Exception {
        try{
            orderRepository.save(order);
        }catch (Exception e){
            throw new Exception();
        }

    }

    @Override
    public void deleteOrder(Long orderIdx) throws Exception {
        try{
            orderRepository.deleteById(orderIdx);
        }catch (Exception e){
            throw new Exception();
        }
    }
}
