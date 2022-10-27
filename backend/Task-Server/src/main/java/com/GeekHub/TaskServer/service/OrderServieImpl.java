package com.GeekHub.TaskServer.service;

import com.GeekHub.TaskServer.dao.OrderDao;
import com.GeekHub.TaskServer.dao.SpotDao;
import com.GeekHub.TaskServer.dto.OrderDto;
import com.GeekHub.TaskServer.entity.Order;
import com.GeekHub.TaskServer.entity.Orderstatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServieImpl implements OrderServie{
    private final OrderDao orderDao;
    private final SpotDao spotDao;

    @Override
    @Transactional
    public List<OrderDto> getOrderAll() throws Exception {
        try{
            List<Order> orderList = orderDao.getOrderAll();
            List<OrderDto> orderDtoList = orderList.stream().map(entity -> OrderDto.of(entity)).collect(Collectors.toList());

            return orderDtoList;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public OrderDto getOrder(Long orderIdx) throws Exception {
        try {
            Order orderEntity = orderDao.getOrder(orderIdx);
            OrderDto orderDto = OrderDto.of(orderEntity);

            return orderDto;
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void createOrder(OrderDto orderDto) throws Exception {
        try {
            Order orderEntity = new Order();
            orderEntity.setOrderStatus(Orderstatus.OFF);
            orderEntity.setAssignTime(LocalDateTime.now());
            orderEntity.setCount(orderDto.getCount());
            orderEntity.setSpot(spotDao.getSpot(orderDto.getSpotIdx()));
            orderDao.saveOrder(orderEntity);
        }catch (Exception e){
            throw new Exception();
        }
    }

    @Override
    @Transactional
    public void updateOrder(OrderDto orderDto) throws Exception {
        Order orderEntity = orderDao.getOrder(orderDto.getOrderIdx()) ;
        orderEntity.setOrderStatus(Orderstatus.OFF);
        orderEntity.setAssignTime(LocalDateTime.now());
        orderEntity.setCount(orderDto.getCount());
        orderEntity.setSpot(spotDao.getSpot(orderDto.getSpotIdx()));
        orderDao.saveOrder(orderEntity);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderIdx) throws Exception {
        orderDao.deleteOrder(orderIdx);
    }

}
