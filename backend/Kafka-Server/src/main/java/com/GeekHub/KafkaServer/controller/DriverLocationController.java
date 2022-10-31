package com.GeekHub.KafkaServer.controller;


import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/driver")
public class DriverLocationController {
    @Autowired
    MessageService messageService;

    @GetMapping("/location")
    public Message getDriverLocation(@RequestParam String driver){
        System.out.println("Controller-check-1");
        return messageService.getMessage(driver);

    }

}
