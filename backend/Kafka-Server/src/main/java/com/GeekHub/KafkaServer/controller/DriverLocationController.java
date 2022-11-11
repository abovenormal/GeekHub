package com.GeekHub.KafkaServer.controller;


import com.GeekHub.KafkaServer.model.Message;
import com.GeekHub.KafkaServer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/location")
public class DriverLocationController {
    @Autowired
    MessageService messageService;

    @GetMapping("/getLocation")
    public ResponseEntity<Message> getDriverLocation(@RequestParam String driver){
        try{
            Message message = messageService.getMessage(driver);
            return new ResponseEntity<>(message, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

    }
    @GetMapping("/getLog")
    public ResponseEntity<List<Message>> getDriverLog(@RequestParam String driver, String date){
        try{
            List<Message> messages = messageService.getLogs(driver,date);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }
    @GetMapping("/getLog50")
    public ResponseEntity<List<Message>> getDriverLog50(@RequestParam String driver, String date){
        try{
            List<Message> messages = messageService.getLogs50(driver,date);
            return new ResponseEntity<>(messages, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
        }


    }

}
