package com.GeekHub.TaskServer.controlloer;

import com.GeekHub.TaskServer.dto.OrderDto;
import com.GeekHub.TaskServer.service.OrderServie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderServie orderService;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getOrderAll(){
        List<OrderDto> result = null;
        try {
            result = orderService.getOrderAll();
        }catch (Exception e){
            throw new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/{order_idx}")
    public ResponseEntity<OrderDto> getOrder(@PathVariable("order_idx") Long orderIdx){
        OrderDto result=null;
        try {
            result = orderService.getOrder(orderIdx);
        }catch (Exception e){
            throw  new RuntimeException();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping
    public ResponseEntity<String> createOrder(@RequestBody OrderDto orderDto){
        try {
            orderService.createOrder(orderDto);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return new ResponseEntity<String>("success",HttpStatus.CREATED);
    }

    @PutMapping("/{order_idx}")
    public ResponseEntity<String> updateOrder(@PathVariable("order_idx") Long orderIdx,@RequestBody OrderDto orderDto){
        try {
            orderDto.setOrderIdx(orderIdx);
            orderService.updateOrder(orderDto);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

    @DeleteMapping("/{order_idx}")
    public ResponseEntity<String> deleteOrder(@PathVariable("order_idx") Long orderIdx){
        try {
            orderService.deleteOrder(orderIdx);
        }catch (Exception e){
            throw new RuntimeException();
        }
        return  new ResponseEntity<String>("success",HttpStatus.OK);
    }

}
